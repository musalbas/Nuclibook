package nuclibook.routes;

import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.ActionLog;
import spark.Request;
import spark.Response;

public class LogoutRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

        //accquire id for action logging
        Integer loggedInId = SecurityUtils.getCurrentUser().getId();

		SecurityUtils.destroyLogin();
        ActionLogger.logAction(ActionLogger.LOG_OUT, (loggedInId) == null ? 0 : loggedInId);
        response.redirect("/login?logged-out=1");
		return null;
	}

}
