package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ExportUtils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class ExportRoute extends DefaultRoute {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle();

        String[] fileSplit = request.params(":file:").split("\\.", 2);
        String table = fileSplit[0];

        try {
            String type = fileSplit[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        String exportData = null;

        if (table.equals("patients")) {
            if (SecurityUtils.requirePermission(P.VIEW_PATIENT_LIST, response)) {
                if (type.equals("csv")) {
                    return ExportUtils.exportCSV(Patient.class);
                }
            }
        }

        if (exportData != null) {
            response.header("Content-Disposition", "attachment");
        }

        return exportData;
    }
}
