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
            renderer.setField("days-since-password-changed", currentUser.getDaysRemainingToPasswordChangePrompt());
            if(Integer.parseInt(currentUser.getDaysRemainingToPasswordChangePrompt()) >= 1
                    && Integer.parseInt(currentUser.getDaysRemainingToPasswordChangePrompt()) <= 9) {
                System.out.println("PASSWORD CHANGE SOON! DAYS REMAINING: " + currentUser.getDaysRemainingToPasswordChangePrompt());
                renderer.setField("show-password-reminder", "yes");
            } else if (Integer.parseInt(currentUser.getDaysRemainingToPasswordChangePrompt()) < 1) {
                System.out.println("FORCE PASSWORD CHANGE, REDIRECT DEM SWAGGERS");
                renderer.setField("force-password-change", "yes");
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
