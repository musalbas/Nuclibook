package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class BookingsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENTS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_BOOKING_CALENDAR, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("bookings.html");

        ActionLogger.logAction(ActionLogger.VIEW_BOOKING_CALENDAR, 0);

		return renderer.render();
	}
}
