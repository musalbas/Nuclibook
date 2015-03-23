package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.CameraUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Camera;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * This route provides the calendar overview of the system's bookings
 */
public class BookingsRoute extends DefaultRoute {

	/**
	 * Handles the request for the bookings page
	 * @param request  Information sent by the client
	 * @param response Information sent to the client
	 * @return A fully rendered version of the bookings page
	 * @throws Exception if something goes wrong, for example, loss of connection with a server
	 */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_APPOINTMENTS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_BOOKING_CALENDAR, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// log view
        ActionLogger.logAction(user, ActionLogger.VIEW_BOOKING_CALENDAR, 0);

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("bookings.html");

		// get cameras
		List<Camera> cameras = CameraUtils.getAllCameras(true);
		renderer.setCollection("cameras", cameras);

		return renderer.render();
	}
}
