package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class DashboardRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		HtmlRenderer renderer = new HtmlRenderer("index.html");
		return renderer.render();
	}
}
