package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class HtmlTestRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		HtmlRenderer renderer = new HtmlRenderer();
		renderer.setTemplateFile(request.params(":file"));
		return renderer.render();
	}
}
