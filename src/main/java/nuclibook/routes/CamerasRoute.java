package nuclibook.routes;

import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.CameraUtils;
import nuclibook.models.Camera;
import nuclibook.models.CameraType;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class CamerasRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("cameras.html");

		// get cameras and add to renderer
		List<Camera> allCameras = CameraUtils.getAllCameras(true);
		renderer.setCollection("cameras", allCameras);

		// get camera types and add to renderer
		List<CameraType> allCameraTypes = CameraTypeUtils.getAllCameraTypes(true);
		renderer.setCollection("camera-types", allCameraTypes);

		return renderer.render();
	}
}
