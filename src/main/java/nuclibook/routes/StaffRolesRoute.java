package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.PermissionUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.models.Permission;
import nuclibook.models.Staff;
import nuclibook.models.StaffRole;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The class presents the staff-roles.html page with data on it to the user if he has a permission to view the page.
 */
public class StaffRolesRoute extends DefaultRoute {
    /**
     * Handles user's request to view staff roles.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the staff-roles.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.VIEW_STAFF_ROLES, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_STAFF_ROLES, 0, "Failed as user does not have permissions for this action");
            return null;
        }

        // start renderer
        HtmlRenderer renderer = getRenderer();
        renderer.setTemplateFile("staff-roles.html");

        // get staff roles and add to renderer
        List<StaffRole> allStaffRoles = StaffRoleUtils.getAllStaffRoles(true);
        renderer.setCollection("staff-roles", allStaffRoles);

        // get permissions and add to renderer
        List<Permission> allPermissions = PermissionUtils.getAllPermissions();
        Collections.sort(allPermissions, new Comparator<Permission>() {
            @Override
            public int compare(Permission o1, Permission o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });
        renderer.setCollection("permissions", allPermissions);

        ActionLogger.logAction(user, ActionLogger.VIEW_STAFF_ROLES, 0);

        return renderer.render();
    }
}
