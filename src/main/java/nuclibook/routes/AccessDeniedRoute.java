package nuclibook.routes;

import spark.Request;
import spark.Response;

/**
 * This class is used when the user doesn't have permission to access a particular page.
 */
public class AccessDeniedRoute extends DefaultRoute {
    /**
     * Presents the rendered template of the access-denied.html page to the user.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the page.
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        prepareToHandle(request);
        getRenderer().setTemplateFile("access-denied.html");
        return getRenderer().render();
    }
}
