package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.ExportUtils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class ExportRoute extends DefaultRoute {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        String[] fileSplit = request.params(":file:").split("\\.", 2);
        String table = fileSplit[0];

        String type = "";
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
            response.header("Content-Disposition", "attachment");
        }

        return exportData;
    }
}
