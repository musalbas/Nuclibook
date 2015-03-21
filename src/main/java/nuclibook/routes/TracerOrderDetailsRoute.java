package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.Staff;
import nuclibook.models.TracerOrder;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

/**
 * The class redirects the user to the tracer-order-details.html page if he has a permission to view the page.
 */
public class TracerOrderDetailsRoute extends DefaultRoute {
    /**
     * method handles user's request to view tracer-order-details.html page.
     *
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return The rendered template of the tracer-order-details.html page.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_TRACERS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_TRACERS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("tracer-order-details.html");

		// get tracer order
		TracerOrder tracerOrder = TracerOrderUtils.getTracerOrder(request.params(":tracerorderid:"));

		// update?
		if (request.params(":newstatus:") != null && user.hasPermission(P.EDIT_TRACERS)) {
			tracerOrder.setStatus(request.params(":newstatus:"));
			AbstractEntityUtils.updateEntity(TracerOrder.class, tracerOrder);
            ActionLogger.logAction(user, ActionLogger.UPDATE_TRACER, tracerOrder.getId());
		}

		// add tracer order to renderer
		if (tracerOrder == null) {
			renderer.setField("no-tracer-order", "yes");
            return renderer.render();
		}

		renderer.setField("no-tracer-order", "no");
		renderer.setBulkFields(tracerOrder.getHashMap());

        ActionLogger.logAction(user, ActionLogger.VIEW_TRACERS, 0);

		return renderer.render();
	}
}
