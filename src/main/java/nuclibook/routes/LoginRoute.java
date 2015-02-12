package nuclibook.routes;

import nuclibook.constants.RequestType;
import nuclibook.models.User;
import nuclibook.server.HtmlRenderer;
import nuclibook.server.SecurityUtils;
import spark.Request;
import spark.Response;

public class LoginRoute extends DefaultRoute {

	private enum Status {
		FAILED_LOGIN,
		INVALID_DETAILS
	}

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
		return handleGet(request, response, null, null);
	}

	public Object handleGet(Request request, Response response, Status status, String userIdPreFill) throws Exception {
		// any status message?
		String msg = null;
		String statusType = null;
		if (status == Status.FAILED_LOGIN) {
			msg = "Incorrect user ID and/or password";
			statusType = "danger";
		}
		if (status == Status.INVALID_DETAILS) {
			msg = "You did not enter a valid user ID";
			statusType = "danger";
		}

		HtmlRenderer renderer = new HtmlRenderer("login.html");
		renderer.setField("message", msg);
		renderer.setField("status", statusType);
		renderer.setField("userid", userIdPreFill);
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
			return handleGet(request, response, Status.INVALID_DETAILS, null);
		}

		// check credentials
		User user = SecurityUtils.attemptLogin(userId, password);
		if (user == null) {
			return handleGet(request, response, Status.FAILED_LOGIN, userId + "");
		} else {
			response.redirect("/");
			return null;
		}
	}
}
