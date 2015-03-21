package nuclibook.routes;

import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;
/**
 * The class redirects the user to the profile.html page which presents information about the user.
 */
public class ProfileRoute extends DefaultRoute {
    /**
     * method handles user's request to view profile.html page.
     *
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return The rendered template of the profile.html page.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

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
