package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class TemplatingTestRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// start renderer
		HtmlRenderer renderer = new HtmlRenderer("test.html");

		// input field values
		renderer.setField("test-field", "It works!");
		renderer.setField("status", "win");

		return renderer.render();
	}
}
