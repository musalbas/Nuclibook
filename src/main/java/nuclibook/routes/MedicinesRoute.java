package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.MedicineUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Medicine;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class MedicinesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_MEDICINES, response)) return null;

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("medicines.html");

		// get medicines and add to renderer
		List<Medicine> allMedicines = MedicineUtils.getAllMedicines(true);
		renderer.setCollection("medicines", allMedicines);

		return renderer.render();
	}
}
