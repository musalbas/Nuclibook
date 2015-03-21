package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffRole;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;
/**
 * The class redirects the user to the staff.html page if he has a permission to view the page.
 */
public class StaffRoute extends DefaultRoute {
    /**
     * method handles user's request to view staff.html page.
     *
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return The rendered template of the staff.html page.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_STAFF, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_STAFF, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("staff.html");

		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("staff", allStaff);

		// get staff roles and add to renderer
		List<StaffRole> allStaffRoles = StaffRoleUtils.getAllStaffRoles(true);
		renderer.setCollection("staff-roles", allStaffRoles);

        ActionLogger.logAction(user, ActionLogger.VIEW_STAFF, 0);

		return renderer.render();
	}
}
