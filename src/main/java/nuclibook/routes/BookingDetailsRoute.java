package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingDetailsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENT_DETAILS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_BOOKING, Integer.parseInt(request.params(":bookingid")), "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("booking-details.html");

		// get booking
		Booking booking = BookingUtils.getBooking(request.params(":bookingid:"));

        // get cameras
        List<Camera> cameras = CameraUtils.getAllCameras(true);

        //get therapy
        Therapy therapy = booking.getTherapy();

		// update?
		if (request.params(":newstatus:") != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_APPOINTMENTS)) {
			booking.setStatus(request.params(":newstatus:"));
			AbstractEntityUtils.updateEntity(Booking.class, booking);
            ActionLogger.logAction(ActionLogger.UPDATE_BOOKING, booking.getId());
		}

		// add booking to renderer
		if (booking == null) {
			renderer.setField("no-booking", "yes");
            return renderer.render();
		}

		renderer.setField("no-patient", "no");
		renderer.setBulkFields(booking.getHashMap());

		// add booking sections
		List<BookingSection> bookingSections = booking.getBookingSections();
		renderer.setCollection("booking-sections", bookingSections);

		// add patient
		Patient patient = booking.getPatient();
		renderer.setBulkFields(patient.getHashMap());

        ActionLogger.logAction(ActionLogger.VIEW_BOOKING, booking.getId());

        // add cameras
        renderer.setCollection("cameras", CameraUtils.getCamerasForTherapy(therapy));




//        renderer.setCollection("tracers", TracerUtils.getAllTracers(true));
//        renderer.setField("default-tracer-id", therapy.getTracerRequired().getId());
//
//        // add tracer dose
//        renderer.setField("therapy-tracer-dose", therapy.getTracerDose());
//
//        // add tracer order date
//        renderer.setField("tracer-order-due", displayBookingSections.isEmpty() ? "" : displayBookingSections.get(0).getStart().minusDays(therapy.getTracerRequired().getOrderTime()).toString("YYYY-MM-dd"));


        // add staff
        List<Staff> allStaff = StaffUtils.getAllStaff(true);
        Collections.sort(allStaff, new Comparator<Staff>() {
            @Override
            public int compare(Staff o1, Staff o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        renderer.setCollection("staff", allStaff);

		return renderer.render();
	}
}
