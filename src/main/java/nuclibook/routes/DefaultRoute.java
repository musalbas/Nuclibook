package nuclibook.routes;

import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Route;

public abstract class DefaultRoute implements Route {

	private RequestType requestType;
	private HtmlRenderer renderer;

	public DefaultRoute() {
		this(RequestType.GET);
	}

	public DefaultRoute(RequestType requestType) {
		// set request type
		this.requestType = requestType;

		// set up renderer
		renderer = new HtmlRenderer();
	}

	public void prepareToHandle() {
		// make sure this is a fresh start
		renderer.clearFields();
		renderer.clearCollections();

		// set up login field
		if (SecurityUtils.checkLoggedIn()) {
			renderer.setField("logged-in", "yes");

			Staff currentUser = SecurityUtils.getCurrentUser();
			renderer.setField("current-user-id", currentUser.getId());
			renderer.setField("current-user-username", currentUser.getUsername());
			renderer.setField("current-user-name", currentUser.getName());
			renderer.setField("current-user-role", currentUser.getRole().getLabel());
			renderer.setField("current-user-permissions-summary", currentUser.getRole().getPermissionSummary());
			renderer.setField("current-user-days-until-password-change", currentUser.getDaysRemainingToPasswordChange());
			if (currentUser.getDaysRemainingToPasswordChange() >= 1 && currentUser.getDaysRemainingToPasswordChange() <= 9) {
				renderer.setField("show-password-reminder", "yes");
			} else if (currentUser.getDaysRemainingToPasswordChange() < 1) {
				// TODO: force redirect to change password page
			}
		} else {
			renderer.setField("logged-in", "no");
		}
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public HtmlRenderer getRenderer() {
		return renderer;
	}
}
