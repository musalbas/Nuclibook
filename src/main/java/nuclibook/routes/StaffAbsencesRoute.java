package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffAbsenceUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffAbsence;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * The class presents the rendered template of the staff-absences.html page with data on it to the user if he has a permission to view the page.
 */
public class StaffAbsencesRoute extends DefaultRoute {
    /**
     * Handles user's request to view staff absences.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the staff-absences.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.VIEW_STAFF_ABSENCES, response)) {
            String staffId = (request.params(":staffid:")) == null ? 0 + "" : request.params(":staffid:");
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_STAFF_ABSENCE, Integer.parseInt(staffId), "Failed as user does not have permissions for this action");
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
        renderer.setTemplateFile("staff-absences.html");

        // add staff fields
        renderer.setField("staff-id", currentStaff.getId());
        renderer.setField("staff-name", currentStaff.getName());

        // add absences
        List<StaffAbsence> allAbsences = StaffAbsenceUtils.getStaffAbsencesByStaffId(currentStaff.getId());
        renderer.setCollection("staff-absences", allAbsences);

        ActionLogger.logAction(user, ActionLogger.VIEW_STAFF_ABSENCE, currentStaff.getId());

        return renderer.render();
    }
}
