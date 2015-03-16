package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.ActionLog;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class ActionLogRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_ACTION_LOG, response)) return null;

		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("action-log.html");

        // get all actions and add to renderer
        List<ActionLog> actionLogs = ActionLogUtils.getAllActions();
        renderer.setCollection("action-logs", actionLogs);

        return renderer.render();
	}
}
