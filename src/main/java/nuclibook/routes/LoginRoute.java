package nuclibook.routes;

import nuclibook.constants.RequestType;
import nuclibook.models.User;
import nuclibook.server.SecurityUtils;
import spark.Request;
import spark.Response;

public class LoginRoute extends DefaultRoute {

	public LoginRoute(RequestType requestType) {
		super(requestType);
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// check they are not already logged in
		if (SecurityUtils.checkLoggedIn()) {
			response.redirect("/");
			return null;
		}

		// handle with GET or POST
		if (getRequestType() == RequestType.POST) {
			return handlePost(request, response);
		} else {
			return handleGet(request, response);
		}
	}

	public Object handleGet(Request request, Response response) throws Exception {
		// TODO: implement actual front end login form

		// start building html output
		String output = "<p>Login form</p>";

		// any message?
		String msg = request.queryParams("msg");
		if (msg != null) {
			if (msg.equals("1")) {
				output += "<p>Invalid login!</p>";
			} else if (msg.equals("2")) {
				output += "<p>You have been logged out</p>";
			}
		}

		// output html
		output += "<form action=\"/login\" method=\"post\">" +
				"<input type=\"text\" name=\"username\" /><br />" +
				"<input type=\"text\" name=\"password\" /><br />" +
				"<input type=\"Submit\" value=\"Login\" />" +
				"</form>";

		return output;
	}

	public Object handlePost(Request request, Response response) throws Exception {
		// get username and password from POST
		String username = request.queryParams("username");
		String password = request.queryParams("password");

		// check credentials
		User user = SecurityUtils.attemptLogin(username, password);
		if (user == null) {
			response.redirect("/login?msg=1");
			return null;
		} else {
			response.redirect("/");
			return null;
		}
	}
}
