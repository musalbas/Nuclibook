package nuclibook.routes;

import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class LoginRoute extends DefaultRoute {

	private HashMap<String, String> rendererFields = new HashMap<>();

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
			return handleGet();
		}
	}

	public Object handleGet() throws Exception {
		// check stage
		if (!rendererFields.containsKey("stage")) {
			rendererFields.put("stage", "1");
		}

		HtmlRenderer renderer = new HtmlRenderer("login.html");
		renderer.setBulkFields(rendererFields);
		return renderer.render();
	}

	public Object handlePost(Request request, Response response) throws Exception {
		// get user id and password from POST
		Integer userId;
		String password;
		try {
			userId = Integer.parseInt(request.queryParams("userid"));
			password = request.queryParams("password");
		} catch (NumberFormatException e) {
			// force failure
			rendererFields.clear();
			rendererFields.put("error-bad-user-id", "");
			return handleGet();
		}

		// is this stage 1 or stage 2?
		if (password == null) {
			// submission from stage 1

			// get user's name
			String userName = "";// TODO: StaffUtils.getStaffName(userId);

			// back to stage 1 of login if no user exists
			if (userName == null) {
				rendererFields.clear();
				rendererFields.put("error-bad-user-id", "");
				rendererFields.put("userid", userId.toString());
				return handleGet();
			}

			// send to stage 2 of login screen
			rendererFields.clear();
			rendererFields.put("userid", userId.toString());
			rendererFields.put("username", userName);
			rendererFields.put("stage", "2");
			return handleGet();
		} else {
			// submission from stage 2

			// check credentials
			Staff staff = SecurityUtils.attemptLogin(userId, password);
			if (staff == null) {
				// sent back to stage 1 of login screen
				rendererFields.clear();
				rendererFields.put("error-bad-password", "");
				rendererFields.put("userid", userId.toString());
				return handleGet();
			} else {
				/*
				if (user.getStatus() != active) {
					rendererFields.clear();
					rendererFields.put("error-bad-status", "");
					rendererFields.put("userid", userId.toString());
					rendererFields.put("stage", "1");
					return handleGet();
				}
				 */
				response.redirect("/");
				return null;
			}
		}
	}
}
