package nuclibook.routes;

import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.MedicineUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.CameraType;
import nuclibook.models.Medicine;
import nuclibook.models.Therapy;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class TherapiesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("therapies.html");

		// get therapies and add to renderer
		List<Therapy> allTherapies = TherapyUtils.getAllTherapies(true);
		renderer.setCollection("therapies", allTherapies);

		// get camera types and add to renderer
		List<CameraType> allCameraTypes = CameraTypeUtils.getAllCameraTypes(true);
		renderer.setCollection("camera-types", allCameraTypes);

		// get medicines and add to renderer
		List<Medicine> allMedicines = MedicineUtils.getAllMedicines(true);
		renderer.setCollection("medicines", allMedicines);

		return renderer.render();
	}
}
