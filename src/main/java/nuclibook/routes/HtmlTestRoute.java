package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class HtmlTestRoute extends DefaultRoute {

	/**
	 * This route provides a testing page for static HTML.
	 */

	public HtmlTestRoute() {
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return new HtmlRenderer(request.params(":file")).render();

	}
}
