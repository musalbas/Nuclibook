package nuclibook.routes;

import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.ActionLog;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;
import spark.Session;
/**
 * This class is called when the user logs out.
 */
public class LogoutRoute extends DefaultRoute {
    /**
     * method redirects to the login page with the appropriate message that states that the user has been logged out.
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return null.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle(request);

		// get current session and user
		Session session = request.session();
		Staff user = SecurityUtils.getCurrentUser(session);

		// require csrf token for logout
		if (!SecurityUtils.checkCsrfToken(session, request.queryParams("csrf-token"))) {
			return null;
		}

        //accquire id for action logging
        Integer loggedIn = user == null ? 0 : user.getId() ;

		SecurityUtils.destroyLogin(session);
        ActionLogger.logAction(user, ActionLogger.LOG_OUT, loggedIn);
        response.redirect("/login?logged-out=1");
		return null;
	}

}
