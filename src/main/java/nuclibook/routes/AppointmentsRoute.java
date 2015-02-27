package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.CameraType;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class AppointmentsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

        // security check
        if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_LIST, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("appointments.html");

        // get patients and add to renderer
        List<Patient> allPatients = PatientUtils.getAllPatients(true);
        renderer.setCollection("patients", allPatients);

		return renderer.render();
	}
}
