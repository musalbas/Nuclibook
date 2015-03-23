package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * This route edits the details of a given booking
 */
public class BookingEditRoute extends DefaultRoute {

	/**
	 * Handles the request by making the relevant changes in the database
	 * @param request  Information sent by the client
	 * @param response Information sent to the client
	 * @return Nothing; this route completes by redirecting
	 * @throws Exception if something goes wrong, for example, loss of connection with a server
	 */
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
		String notes = request.queryParams("notes-all");

		// get assigned staff
		ArrayList<Staff> assignedStaff = new ArrayList<>();
		String[] assignedStaffIds = request.queryParams("current-staff-id").split(", ");
		for (String staffId : assignedStaffIds) {
			if (!staffId.equals("0")) {
				Staff staff = StaffUtils.getStaff(staffId);
				if (staff != null) assignedStaff.add(staff);
			}
		}

		// get booking sections
		ArrayList<BookingSection> bookingSections = new ArrayList<>();
		String[] bookingSectionsStringArray = request.queryParams("booking-sections-as-string-time-only").split(", ");
		for (int i = 0; i < bookingSectionsStringArray.length; ++i) {
			String bookingDate = bookingSectionsStringArray[i].split(" - ")[0].substring(0, 10);
			String startTime = bookingDate + "T" + bookingSectionsStringArray[i].substring(10, 15);
			String endTime = bookingDate + "T" + bookingSectionsStringArray[i].substring(15);
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
			response.redirect("/");
			return null;
		}

		// save booking in DB
		Booking booking = BookingUtils.getBooking(request.queryParams("bookingid"));
		booking.setCamera(camera);
		booking.setTracer(tracer);
		booking.setTracerDose(tracerDose);
		booking.setNotes(notes);
		AbstractEntityUtils.updateEntity(Booking.class, booking);

		// delete old booking sections
		List<BookingSection> oldBookingSections = booking.getBookingSections();
		for (BookingSection bs : oldBookingSections) {
			AbstractEntityUtils.deleteEntity(BookingSection.class, bs);
		}

		// add booking sections
		for (BookingSection bs : bookingSections) {
			bs.setBooking(booking);
			AbstractEntityUtils.createEntity(BookingSection.class, bs);
		}

		// delete old booking sections
		List<BookingStaff> oldStaff = booking.getBookingStaff();
		for (BookingStaff st : oldStaff) {
			AbstractEntityUtils.deleteEntity(BookingStaff.class, st);
		}
		// add staff
		for (Staff s : assignedStaff) {
			BookingStaff bs = new BookingStaff(booking, s);
			AbstractEntityUtils.createEntity(BookingStaff.class, bs);
		}

		// log update
		ActionLogger.logAction(user, ActionLogger.UPDATE_BOOKING, booking.getId());

		// forward to booking details
		response.redirect("/booking-details/" + booking.getId());
		return null;
	}
}
