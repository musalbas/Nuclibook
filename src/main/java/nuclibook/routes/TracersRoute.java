package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.TracerUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Tracer;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class TracersRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_TRACERS, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("tracers.html");

		// get tracers and add to renderer
		List<Tracer> allTracers = TracerUtils.getAllTracers(true);
		renderer.setCollection("tracers", allTracers);

		return renderer.render();
	}
}
