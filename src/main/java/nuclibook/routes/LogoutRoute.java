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
        Integer loggedIn = SecurityUtils.getCurrentUser() == null ? 0 : SecurityUtils.getCurrentUser().getId() ;

		SecurityUtils.destroyLogin();
        ActionLogger.logAction(ActionLogger.LOG_OUT, loggedIn);
        response.redirect("/login?logged-out=1");
		return null;
	}

}
