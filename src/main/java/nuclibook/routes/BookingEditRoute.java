package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class BookingEditRoute extends DefaultRoute {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.EDIT_APPOINTMENTS, response)) return null;

        // get basic info from post
        Patient patient = PatientUtils.getPatient(request.queryParams("patient"));
        Therapy therapy = TherapyUtils.getTherapy(request.queryParams("therapy"));
        Camera camera = CameraUtils.getCamera(request.queryParams("camera"));
        Tracer tracer = TracerUtils.getTracer(request.queryParams("tracer"));
        String tracerDose = request.queryParams("tracer-dose");
        String notes = request.queryParams("notes");

        // get assigned staff
        ArrayList<Staff> assignedStaff = new ArrayList<>();
        String[] assignedStaffIds = request.queryParams("current-staff-id").split(", ");
        for (int i = 0; i < assignedStaffIds.length; ++i) {
            System.out.println(assignedStaffIds[i]);
        }
        for (String staffId : assignedStaffIds) {
            if (!staffId.equals("0")) {
                Staff staff = StaffUtils.getStaff(staffId);
                if (staff != null) assignedStaff.add(staff);
            }
        }

        // get booking sections
        ArrayList<BookingSection> bookingSections = new ArrayList<>();
        String[] bookingSectionsStringArray = request.queryParams("booking-sections-as-string-time-only").split(", ");
        String bookingDate = request.queryParams("date");
        for (int i = 0; i < bookingSectionsStringArray.length; ++i) {
            String startTime = bookingDate + "T" + bookingSectionsStringArray[i].split(" - ")[0];
            String endTime = bookingDate + "T" + bookingSectionsStringArray[i].split(" - ")[1];
            BookingSection tempBookingSection = new BookingSection(new DateTime(startTime), new DateTime(endTime));
            bookingSections.add(tempBookingSection);
        }

        // check the data received - should have all be validated on the front end,
        // so just check that we got everything or fail
        if (patient == null
                || therapy == null
                || camera == null
                || tracer == null
                || tracerDose == null
                || tracerDose.length() == 0
                || bookingSections.size() == 0) {
            System.out.println(patient + " " + therapy + " " + camera + " " + tracer + " " + tracerDose.length() + bookingSections.size());
            response.redirect("/");
            return null;
        }


        // save booking in DB
        Booking booking = BookingUtils.getBooking(request.queryParams("bookingid"));
        booking.setCamera(camera);
        booking.setTracer(tracer);
        booking.setTracerDose(tracerDose);
        booking.setNotes(request.queryParams("notes"));
        booking.setStatus("unconfirmed");
        AbstractEntityUtils.updateEntity(Booking.class, booking);

        //delete old booking sections
        List<BookingSection> oldBookingSections = booking.getBookingSections();
        for (BookingSection bs : oldBookingSections) {
            AbstractEntityUtils.deleteEntity(BookingSection.class, bs);
        }

        // add booking sections
        for (BookingSection bs : bookingSections) {
            bs.setBooking(booking);
            AbstractEntityUtils.createEntity(BookingSection.class, bs);
        }

        //delete old booking sections
        List<BookingStaff> oldStaff = booking.getBookingStaff();
        for (BookingStaff st : oldStaff) {
            AbstractEntityUtils.deleteEntity(BookingStaff.class, st);
        }
        // add staff
        for (Staff s : assignedStaff) {
            BookingStaff bs = new BookingStaff(booking, s);
            AbstractEntityUtils.createEntity(BookingStaff.class, bs);
        }

        // log creation
        ActionLogger.logAction(user, ActionLogger.UPDATE_BOOKING, booking.getId());

        // forward to booking details
        response.redirect("/booking-details/" + booking.getId());
        return null;
    }
}
