package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
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
		if (!SecurityUtils.requirePermission(P.VIEW_TRACERS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_TRACERS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("tracer-order-details.html");

		// get tracer order
		TracerOrder tracerOrder = TracerOrderUtils.getTracerOrder(request.params(":tracerorderid:"));

		// update?
		if (request.params(":newstatus:") != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_TRACERS)) {
			tracerOrder.setStatus(request.params(":newstatus:"));
			AbstractEntityUtils.updateEntity(TracerOrder.class, tracerOrder);
            ActionLogger.logAction(ActionLogger.UPDATE_TRACER, tracerOrder.getId());
		}

		// add tracer order to renderer
		if (tracerOrder == null) {
			renderer.setField("no-tracer-order", "yes");
            return renderer.render();
		}

		renderer.setField("no-tracer-order", "no");
		renderer.setBulkFields(tracerOrder.getHashMap());

        ActionLogger.logAction(ActionLogger.VIEW_TRACERS, 0);

		return renderer.render();
	}
}
