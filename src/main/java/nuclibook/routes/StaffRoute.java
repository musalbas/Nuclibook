package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffRole;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class StaffRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_STAFF, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("staff.html");

		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("staff", allStaff);

		// get staff roles and add to renderer
		List<StaffRole> allStaffRoles = StaffRoleUtils.getAllStaffRoles(true);
		renderer.setCollection("staff-roles", allStaffRoles);

		return renderer.render();
	}
}
