package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.TracerUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.CameraType;
import nuclibook.models.Tracer;
import nuclibook.models.Therapy;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class TherapiesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_THERAPIES, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("therapies.html");

		// get therapies and add to renderer
		List<Therapy> allTherapies = TherapyUtils.getAllTherapies(true);
		renderer.setCollection("therapies", allTherapies);

		// get camera types and add to renderer
		List<CameraType> allCameraTypes = CameraTypeUtils.getAllCameraTypes(true);
		renderer.setCollection("camera-types", allCameraTypes);

		// get tracers and add to renderer
		List<Tracer> allTracers = TracerUtils.getAllTracers(true);
		renderer.setCollection("tracers", allTracers);

		return renderer.render();
	}
}
