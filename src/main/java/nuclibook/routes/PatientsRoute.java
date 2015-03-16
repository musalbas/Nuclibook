package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class PatientsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_LIST, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_PATIENTS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("patients.html");

		// get patients and add to renderer
		List<Patient> allPatients = PatientUtils.getAllPatients(true);
		renderer.setCollection("patients", allPatients);

        ActionLogger.logAction(ActionLogger.VIEW_PATIENTS, 0);

        return renderer.render();
	}
}
