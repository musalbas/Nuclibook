package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.Patient;
import nuclibook.models.Therapy;
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

		// get therapies and add to renderer
		List<Therapy> allTherapies = TherapyUtils.getAllTherapies(true);
		renderer.setCollection("therapies", allTherapies);
		
		return renderer.render();
	}
}
