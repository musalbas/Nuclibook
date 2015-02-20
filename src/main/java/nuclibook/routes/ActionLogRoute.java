package nuclibook.routes;

import nuclibook.entity_utils.ActionLogUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.ActionLog;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class ActionLogRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("activity.html");

        // get all actions and add to renderer
        List<ActionLog> actionLogs = ActionLogUtils.getAllActions();
        renderer.setCollection("action-logs", actionLogs);

        return renderer.render();
	}
}
