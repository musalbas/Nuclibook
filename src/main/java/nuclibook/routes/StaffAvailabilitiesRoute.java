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

/**
 * The class presents rendered template of the staff-availabilities.html page with data on it to the user if he has a permission to view the page.
 */
public class StaffAvailabilitiesRoute extends DefaultRoute {
    /**
     * Handles user's request to view staff availabilities.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the staff-availabilities.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.VIEW_STAFF_AVAILABILITIES, response)) {
            String staffId = (request.params(":staffid:")) == null ? 0 + "" : request.params(":staffid:");
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_STAFF_AVAILABILITY, Integer.parseInt(staffId), "Failed as user does not have permissions for this action");
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

        ActionLogger.logAction(user, ActionLogger.VIEW_STAFF_AVAILABILITY, currentStaff.getId());

        return renderer.render();
    }
}
