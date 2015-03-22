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
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_APPOINTMENT_DETAILS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_BOOKING, Integer.parseInt(request.params(":bookingid")), "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("booking-details.html");

		// get booking
		Booking booking = BookingUtils.getBooking(request.params(":bookingid:"));

		// add booking to renderer
		if (booking == null) {
			renderer.setField("no-booking", "yes");
            return renderer.render();
		}
		renderer.setBulkFields(booking.getHashMap());

		// update?
		if (request.params(":newstatus:") != null && user.hasPermission(P.EDIT_APPOINTMENTS)) {
			if (!SecurityUtils.checkCsrfToken(request.session(), request.queryParams("csrf-token"))) {
				return null;
			}
			booking.setStatus(request.params(":newstatus:"));
			AbstractEntityUtils.updateEntity(Booking.class, booking);
            ActionLogger.logAction(user, ActionLogger.UPDATE_BOOKING, booking.getId());
		}

        ActionLogger.logAction(user, ActionLogger.VIEW_BOOKING, booking.getId());

        // get therapy
        Therapy therapy = booking.getTherapy();

		// add booking sections
		List<BookingSection> bookingSections = booking.getBookingSections();
		renderer.setCollection("booking-sections", bookingSections);

		// add patient
		Patient patient = booking.getPatient();
		renderer.setBulkFields(patient.getHashMap());
        renderer.setField("patient-id", patient.getId());

        //add therapy
        renderer.setField("therapy-id", therapy.getId());
        // add cameras
        renderer.setCollection("cameras", CameraUtils.getCamerasForTherapy(therapy));

        // add tracers
        renderer.setCollection("tracers", TracerUtils.getAllTracers(true));
        renderer.setField("default-tracer-id", therapy.getTracerRequired().getId());

        // add tracer dose
        renderer.setField("therapy-tracer-dose", therapy.getTracerDose());

        // add staff
        List<Staff> allStaff = StaffUtils.getAllStaff(true);
        Collections.sort(allStaff, new Comparator<Staff>() {
            @Override
            public int compare(Staff o1, Staff o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        renderer.setCollection("staff", allStaff);

        // add notes
        renderer.setField("notes-all", "test");

        return renderer.render();
	}
}
