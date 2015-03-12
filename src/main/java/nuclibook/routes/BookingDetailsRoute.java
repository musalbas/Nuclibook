package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
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
		if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENTS, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("booking-details.html");

		// get booking
		Booking booking = BookingUtils.getBooking(request.params(":bookingid:"));

		// update?
		if (request.params(":newstatus:") != null) {
			booking.setStatus(request.params(":newstatus:"));
			AbstractEntityUtils.updateEntity(Booking.class, booking);
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

		return renderer.render();
	}
}
