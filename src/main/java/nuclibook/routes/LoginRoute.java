package nuclibook.routes;

import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
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
		// get staff id and password from POST
		String username;
		String password;
		try {
			username = request.queryParams("username");
			password = request.queryParams("password");
		} catch (NumberFormatException e) {
			// force failure
			rendererFields.clear();
			rendererFields.put("error-bad-staff-id", "");
			return handleGet();
		}

		// is this stage 1 or stage 2?
		if (password == null) {
			// submission from stage 1

			// TODO: get staff's name
			/*Staff staff = StaffUtils.getStaffByUsername(username);
			String staffName = staff.getName();

			// back to stage 1 of login if no staff exists
			if (staffName == null) {
				rendererFields.clear();
				rendererFields.put("error-bad-staff-id", "");
				rendererFields.put("username", username);
				return handleGet();
			}*/

			// send to stage 2 of login screen
			rendererFields.clear();
			rendererFields.put("username", username);
			// TODO: rendererFields.put("staffname", staffName);
			rendererFields.put("stage", "2");
			return handleGet();
		} else {
			// submission from stage 2

			// check credentials
			Staff staff = SecurityUtils.attemptLogin(username, password);
			if (staff == null) {
				// sent back to stage 1 of login screen
				rendererFields.clear();
				rendererFields.put("error-bad-password", "");
				rendererFields.put("username", username);
				return handleGet();
			} else {
				/* TODO
				if (staff.getStatus() != active) {
					rendererFields.clear();
					rendererFields.put("error-bad-status", "");
					rendererFields.put("username", username.toString());
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
