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

public class BookingsRoute extends DefaultRoute {

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
