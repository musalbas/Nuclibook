package nuclibook.routes;

import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.ActionLog;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;
import spark.Session;

public class LogoutRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle(request);

		// get current session and user
		Session session = request.session();
		Staff user = SecurityUtils.getCurrentUser(session);

        //accquire id for action logging
        Integer loggedIn = user == null ? 0 : user.getId() ;

		SecurityUtils.destroyLogin(session);
        ActionLogger.logAction(user, ActionLogger.LOG_OUT, loggedIn);
        response.redirect("/login?logged-out=1");
		return null;
	}

}
