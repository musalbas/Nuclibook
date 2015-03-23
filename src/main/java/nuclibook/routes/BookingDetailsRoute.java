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

/**
 * This route provides details of a given booking and allows for status changes
 */
public class BookingDetailsRoute extends DefaultRoute {

	/**
	 * Handles the request for a booking detail page
	 * @param request  Information sent by the client
	 * @param response Information sent to the client
	 * @return A fully rendered version of the booking details page
	 * @throws Exception if something goes wrong, for example, loss of connection with a server
	 */
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

		// update status?
		if (request.params(":newstatus:") != null && user.hasPermission(P.EDIT_APPOINTMENTS)) {
			if (!SecurityUtils.checkCsrfToken(request.session(), request.queryParams("csrf-token"))) {
				return null;
			}
			booking.setStatus(request.params(":newstatus:"));
			AbstractEntityUtils.updateEntity(Booking.class, booking);
			ActionLogger.logAction(user, ActionLogger.UPDATE_BOOKING, booking.getId());
		}

		// add booking to renderer
		if (booking == null) {
			renderer.setField("no-booking", "yes");
			return renderer.render();
		}
		renderer.setBulkFields(booking.getHashMap());

		// log action
		ActionLogger.logAction(user, ActionLogger.VIEW_BOOKING, booking.getId());

		/*
		add details for editing
		 */

		// add therapy
		Therapy therapy = booking.getTherapy();
		renderer.setField("therapy-id", therapy.getId());

		// add booking sections
		List<BookingSection> bookingSections = booking.getBookingSections();
		renderer.setCollection("booking-sections", bookingSections);

		// add patient
		Patient patient = booking.getPatient();
		renderer.setBulkFields(patient.getHashMap());
		renderer.setField("patient-id", patient.getId());

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
				if (o1 == null || o1.getName() == null) return 1;
				if (o2 == null || o2.getName() == null) return -1;
				return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
			}
		});
		renderer.setCollection("staff", allStaff);

		// add notes
		renderer.setField("notes-all", booking.getNotes());

		return renderer.render();
	}
}
