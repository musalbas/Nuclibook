package nuclibook.routes;

import nuclibook.entity_utils.MedicineUtils;
import nuclibook.models.Medicine;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class MedicinesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("medicines.html");

		// get medicines and add to renderer
		List<Medicine> allMedicines = MedicineUtils.getAllMedicines(true);
		renderer.setCollection("medicines", allMedicines);

		return renderer.render();
	}
}
