package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Booking;
import nuclibook.models.BookingSection;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

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

		return renderer.render();
	}
}
