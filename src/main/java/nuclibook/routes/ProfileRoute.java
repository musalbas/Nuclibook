package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class ProfileRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("profile.html");

        if (request.queryParams("changepw") != null && request.queryParams("changepw").equals("1")) {
            renderer.setField("open-password-modal", "yes");
        }

        if (request.queryParams("force") != null && request.queryParams("force").equals("1")) {
            renderer.setField("force-password-change", "yes");
        }

		return renderer.render();
	}
}
