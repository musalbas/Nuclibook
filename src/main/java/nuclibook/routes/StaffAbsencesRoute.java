package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffAbsenceUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffAbsence;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class StaffAbsencesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_STAFF_ABSENCES, response)) return null;

		// get staff member
		Staff currentStaff = StaffUtils.getStaff(request.params(":staffid:"));
		if (currentStaff == null) {
			response.redirect("/");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("staff-absences.html");

		// add staff fields
		renderer.setField("staff-id", currentStaff.getId());
		renderer.setField("staff-name", currentStaff.getName());

		// add absences
		List<StaffAbsence> allAbsences = StaffAbsenceUtils.getStaffAbsencesByStaffId(currentStaff.getId());
		renderer.setCollection("staff-absences", allAbsences);

		return renderer.render();
	}
}
