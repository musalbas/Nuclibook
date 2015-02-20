package nuclibook.routes;

import nuclibook.entity_utils.PatientUtils;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class PatientsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("patients.html");

		// get patients and add to renderer
		List<Patient> allPatients = PatientUtils.getAllPatients(true);
		renderer.setCollection("patients", allPatients);

		return renderer.render();
	}
}
