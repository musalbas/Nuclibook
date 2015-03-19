package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.CameraType;
import nuclibook.models.Staff;
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
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_THERAPIES, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_THERAPIES, 0, "Failed as user does not have permissions for this action");
            return null;
        }

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

        ActionLogger.logAction(user, ActionLogger.VIEW_THERAPIES, 0);

		return renderer.render();
	}
}
