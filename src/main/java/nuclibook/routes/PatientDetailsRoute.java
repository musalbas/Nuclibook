package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

public class PatientDetailsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_DETAILS, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("patient-details.html");

		// add patient
		Patient patient	= PatientUtils.getPatient(request.params(":patientid:"));
		if (patient == null) {
			renderer.setField("no-patient", "yes");
		} else {
			renderer.setField("no-patient", "no");
			renderer.setBulkFields(patient.getHashMap());
		}

		return renderer.render();
	}
}
