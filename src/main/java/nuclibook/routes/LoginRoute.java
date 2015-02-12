package nuclibook.routes;

import nuclibook.constants.C;
import nuclibook.constants.RequestType;
import nuclibook.models.User;
import nuclibook.server.HtmlRenderer;
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
		// any status message?
		String statusCookie = request.cookie("login-status");
		String msg = null;
		if (statusCookie != null) {
			if (statusCookie.equals(C.LOGIN_STATUS_COOKIE_VALUE_FAILED))
				msg = "Incorrect user ID and/or password!";

			if (statusCookie.equals(C.LOGIN_STATUS_COOKIE_VALUE_LOGGED_OUT))
				msg = "You have been logged out.";

			response.removeCookie(C.LOGIN_STATUS_COOKIE_NAME);
		}

		HtmlRenderer renderer = new HtmlRenderer("login.html");
		renderer.setField("message", msg);
		return renderer.render();
	}

	public Object handlePost(Request request, Response response) throws Exception {
		// get user id and password from POST
		int userId;
		String password;
		try {
			userId = Integer.parseInt(request.queryParams("userid"));
			password = request.queryParams("password");
		} catch (NumberFormatException e) {
			// force failure
			response.cookie(C.LOGIN_STATUS_COOKIE_NAME, C.LOGIN_STATUS_COOKIE_VALUE_FAILED);
			response.redirect("/login");
			return null;
		}

		// check credentials
		User user = SecurityUtils.attemptLogin(userId, password);
		if (user == null) {
			response.cookie(C.LOGIN_STATUS_COOKIE_NAME, C.LOGIN_STATUS_COOKIE_VALUE_FAILED);
			response.redirect("/login");
			return null;
		} else {
			response.redirect("/");
			return null;
		}
	}
}
