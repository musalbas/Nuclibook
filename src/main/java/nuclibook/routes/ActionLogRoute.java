package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class ActionLogRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_ACTION_LOG, response)) {
			ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_ACTION_LOG, 0, "Failed as user does not have permissions for this action");
			return null;
		}

		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("action-log.html");

		ActionLogger.logAction(user, ActionLogger.VIEW_ACTION_LOG, 0);

		return renderer.render();
	}
}
