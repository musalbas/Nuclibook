package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;

/**
 * The class is called when the user wants to import patients details
 */
public class ImportRoute extends DefaultRoute {
    /**
     * Method handles user's request to import patients details.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return status value of the request: OKAY, no_permission, failed_validation, error
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // get request info
        String table = request.queryParams("table");
        String csvData = request.queryParams("csv-data");

        if (table.equals("patients")) {
            if (user == null || !user.hasPermission(P.IMPORT_PATIENTS)) {
                ActionLogger.logAction(user, ActionLogger.ATTEMPT_IMPORT_PATIENTS, 0, "Failed as user does not have permissions for this action");
                return "no_permission";
            }

            Integer[] importResult = new Integer[]{0, 0};
            try {
                importResult = PatientUtils.importPatientsCSV(csvData);
            } catch (Exception e) {
                return "failed_validation";
            }

            ActionLogger.logAction(user, ActionLogger.IMPORT_PATIENTS, 0);

            return "OKAY:" + importResult[0].toString() + " rows successfully imported; " + importResult[1].toString() + " failed to import.";
        }

        // fail safe
        return "error";
    }
}
