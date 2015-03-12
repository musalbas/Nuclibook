package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.TracerOrder;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class TracerOrdersRoute extends DefaultRoute {

	private boolean showAll;

	public TracerOrdersRoute(boolean showAll) {
		this.showAll = showAll;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_TRACERS, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("tracer-orders.html");

		// get tracers and add to renderer
		List<TracerOrder> allTracerOrders = TracerOrderUtils.getAllTracerOrders(!showAll);
		renderer.setCollection("tracer-orders", allTracerOrders);

		// show-all field
		renderer.setField("show-all", showAll ? "yes" : "no");

		return renderer.render();
	}
}
