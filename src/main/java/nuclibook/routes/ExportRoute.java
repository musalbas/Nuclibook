package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.ExportUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The class is called when the user wants to export patients details
 */
public class ExportRoute extends DefaultRoute {
    /**
     * Method handles user's request to export patients details.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return exported data
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current session and user
        Session session = request.session();
        if (request.queryParams("token") != null) {
            session = SecurityUtils.checkOneOffSession(request.queryParams("token"));
        }
        Staff user = SecurityUtils.getCurrentUser(session);

        String[] fileSplit = request.params(":file:").split("\\.", 2);
        String table = fileSplit[0];

        String type;
        try {
            type = fileSplit[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        String exportData = null;

        if (table.equals("patients")) {
            if (SecurityUtils.requirePermission(user, P.EXPORT_PATIENTS, response)) {
                if (type.equals("csv")) {
                    exportData = ExportUtils.exportCSV(Patient.class);
                }
                ActionLogger.logAction(user, ActionLogger.EXPORT_PATIENTS, 0);
            } else {
                ActionLogger.logAction(user, ActionLogger.ATTEMPT_EXPORT_PATIENTS, 0, "Failed as user does not have permissions for this action");
            }
        }

        if (exportData != null) {
            response.header("Content-Disposition", "attachment; filename=\"" + table + "." + type + "\"");
        }

        return exportData;
    }
}
