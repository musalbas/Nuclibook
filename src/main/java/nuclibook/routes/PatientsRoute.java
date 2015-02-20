package nuclibook.routes;

import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
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

		// TODO: change to patients
		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("patients", allStaff);

		return renderer.render();
	}
}
