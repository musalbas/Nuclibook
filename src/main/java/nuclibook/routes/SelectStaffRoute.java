package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class SelectStaffRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_STAFF, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("select-staff.html");

		// check fields
		if (request.params(":target:") == null) {
			response.redirect("/");
			return null;
		}

		if (request.params(":target:").equals("absences")) {
			renderer.setField("target", "staff-absences");
			renderer.setField("subject", "staff absences");
			renderer.setField("current-page", "staff-absences");
		} else if (request.params(":target:").equals("availabilities")) {
			renderer.setField("target", "staff-availabilities");
			renderer.setField("subject", "staff availabilities");
			renderer.setField("current-page", "staff-availabilities");
		} else {
			response.redirect("/");
			return null;
		}

		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("staff", allStaff);

		return renderer.render();
	}
}
