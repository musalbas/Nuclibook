package nuclibook.routes;

import nuclibook.server.SecurityUtils;
import spark.Request;
import spark.Response;

public class LogoutRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		SecurityUtils.destroyLogin();
		response.redirect("/login?msg=2");
		return null;
	}

}
