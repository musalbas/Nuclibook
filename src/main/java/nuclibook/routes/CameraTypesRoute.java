package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.CameraType;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class CameraTypesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_CAMERAS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_CAMERA_TYPES, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("camera-types.html");

		// get camera types and add to renderer
		List<CameraType> allCameraTypes = CameraTypeUtils.getAllCameraTypes(true);
		renderer.setCollection("camera-types", allCameraTypes);

        ActionLogger.logAction(ActionLogger.VIEW_CAMERA_TYPES, 0);

		return renderer.render();
	}
}
