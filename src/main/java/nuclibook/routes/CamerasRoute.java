package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.CameraUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Camera;
import nuclibook.models.CameraType;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class CamerasRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_CAMERAS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_CAMERAS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("cameras.html");

		// get cameras and add to renderer
		List<Camera> allCameras = CameraUtils.getAllCameras(true);
		renderer.setCollection("cameras", allCameras);

		// get camera types and add to renderer
		List<CameraType> allCameraTypes = CameraTypeUtils.getAllCameraTypes(true);
		renderer.setCollection("camera-types", allCameraTypes);

        ActionLogger.logAction(ActionLogger.VIEW_CAMERAS, 0);

		return renderer.render();
	}
}
