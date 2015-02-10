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
		if (getRequestType() == RequestType.GET) {
			return handleGet(request, response);
		} else {
			return handlePost(request, response);
		}
	}

	public Object handleGet(Request request, Response response) throws Exception {
		// TODO: implement actual front end login form
		return "<form action=\"/login\" method=\"post\"><input type=\"text\" name=\"username\" /><br /><input type=\"text\" name=\"password\" /><br /><input type=\"Submit\" value=\"Login\" /></form>";
	}

	public Object handlePost(Request request, Response response) throws Exception {
		return "POSTED: " + request.queryParams("username") + " / " + request.queryParams("password");
	}
}
