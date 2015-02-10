package nuclibook.routes;

import nuclibook.constants.RequestType;
import spark.Request;
import spark.Response;

public class LoginRoute extends DefaultRoute {

	public LoginRoute(RequestType requestType) {
		super(requestType);
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return "";
	}
}
