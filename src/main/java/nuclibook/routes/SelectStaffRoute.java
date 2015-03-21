package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;
/**
 * The class redirects the user to the select-staff.html page if he has a permission to view the page.
 */
public class SelectStaffRoute extends DefaultRoute {
    /**
     * method handles user's request to view select-staff.html page.
     *
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return The rendered template of the select-staff.html page.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

        Integer action = null;
		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_STAFF, response)) {
            action = (request.params(":target:").equals("absences"))? ActionLogger.ATTEMPT_VIEW_STAFF_ABSENCES
                    : (request.params(":target:").equals("availabilities")) ? ActionLogger.ATTEMPT_VIEW_STAFF_AVAILABILITIES : null;
            ActionLogger.logAction(user, action, 0, "Failed as user does not have permissions for this action");
            return null;
        }

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
            action = ActionLogger.VIEW_STAFF_ABSENCES;
		} else if (request.params(":target:").equals("availabilities")) {
			renderer.setField("target", "staff-availabilities");
			renderer.setField("subject", "staff availabilities");
			renderer.setField("current-page", "staff-availabilities");
            action = ActionLogger.VIEW_STAFF_AVAILABILITIES;
		} else {
			response.redirect("/");
			return null;
		}

		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("staff", allStaff);

        ActionLogger.logAction(user, action, 0);

		return renderer.render();
	}
}
