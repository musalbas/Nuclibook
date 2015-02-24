package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class StaffAbsencesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.EDIT_STAFF_ABSENCES, response)) return null;

		// get staff member
		Staff currentStaff = StaffUtils.getStaff(request.params(":staffid:"));
		if (currentStaff == null) {
			response.redirect("/");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("staff-absences.html");

		// add name field
		renderer.setField("staff-name", currentStaff.getName());

		return renderer.render();
	}
}
