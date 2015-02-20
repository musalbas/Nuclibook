package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class DashboardRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("index.html");
		return renderer.render();
	}
}
