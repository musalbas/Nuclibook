package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class ChangePasswordRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("change-password.html");

		return renderer.render();
	}
}
