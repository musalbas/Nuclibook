package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.PermissionUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.models.Permission;
import nuclibook.models.StaffRole;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StaffRolesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		//prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_STAFF_ROLES, response)) return null;

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

		return renderer.render();
	}
}
