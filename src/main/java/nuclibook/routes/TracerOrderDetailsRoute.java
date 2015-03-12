package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.TracerOrder;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class TracerOrderDetailsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_TRACERS, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("tracer-order-details.html");

		// add tracer order
		TracerOrder tracerOrder = TracerOrderUtils.getTracerOrder(request.params(":tracerorderid:"));
		if (tracerOrder == null) {
			renderer.setField("no-tracer-order", "yes");
			return renderer.render();
		}

		renderer.setField("no-tracer-order", "no");
		renderer.setBulkFields(tracerOrder.getHashMap());

		return renderer.render();
	}
}
