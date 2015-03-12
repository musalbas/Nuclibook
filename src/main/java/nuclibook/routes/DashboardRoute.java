package nuclibook.routes;

import nuclibook.entity_utils.BookingUtils;
import nuclibook.models.Booking;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class DashboardRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("dashboard.html");

		// get unconfirmed bookings
		List<Booking> unconfirmedBookings = BookingUtils.getBookingsByStatus("unconfirmed");
		renderer.setCollection("unconfirmed-bookings", unconfirmedBookings);

		return renderer.render();
	}
}
