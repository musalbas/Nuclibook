package nuclibook.routes;

import nuclibook.entity_utils.SecurityUtils;
import spark.Request;
import spark.Response;

public class LogoutRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		SecurityUtils.destroyLogin();
		response.redirect("/login?logged-out=1");
		return null;
	}

}
