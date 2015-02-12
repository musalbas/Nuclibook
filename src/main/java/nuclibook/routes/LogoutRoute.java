package nuclibook.routes;

import nuclibook.constants.C;
import nuclibook.server.SecurityUtils;
import spark.Request;
import spark.Response;

public class LogoutRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		SecurityUtils.destroyLogin();
		response.cookie(C.LOGIN_STATUS_COOKIE_NAME, C.LOGIN_STATUS_COOKIE_VALUE_LOGGED_OUT);
		response.redirect("/login");
		return null;
	}

}
