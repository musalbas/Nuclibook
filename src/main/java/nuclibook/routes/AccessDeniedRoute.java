package nuclibook.routes;

import spark.Request;
import spark.Response;

public class AccessDeniedRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		//prepareToHandle();
		getRenderer().setTemplateFile("access-denied.html");
		return getRenderer().render();
	}
}
