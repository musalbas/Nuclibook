package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffAvailability;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class StaffAvailabilitiesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_STAFF_AVAILABILITIES, response)) {
            String staffId = (request.params(":staffid:")) == null ? 0 + "" : request.params(":staffid:");
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_STAFF_AVAILABILITY, Integer.parseInt(staffId), "Failed as user does not have permissions for this action");
            return null;
        }

		// get staff member
		Staff currentStaff = StaffUtils.getStaff(request.params(":staffid:"));
		if (currentStaff == null) {
			response.redirect("/");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("staff-availabilities.html");

		// add staff fields
		renderer.setField("staff-id", currentStaff.getId());
		renderer.setField("staff-name", currentStaff.getName());

		// add absences
		List<StaffAvailability> allAvailabilities = StaffAvailabilityUtils.getAvailabilitiesByStaffId(currentStaff.getId());
		renderer.setCollection("staff-availabilities", allAvailabilities);

        ActionLogger.logAction(ActionLogger.VIEW_STAFF_AVAILABILITY, currentStaff.getId());

		return renderer.render();
	}
}
