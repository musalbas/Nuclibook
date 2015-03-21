package nuclibook.routes;

import spark.Request;
import spark.Response;
/**
 * The class is called when the user wants to renew the session.
 */
public class RenewSessionRoute extends DefaultRoute {
    /**
     * method handles user's request to renew the session.
     *
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return empty string.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// do nothing
		return "";
	}

}
