package nuclibook.routes;

import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class MedicinesRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// start renderer
		HtmlRenderer renderer = new HtmlRenderer("medicines.html");

		// TODO: change to medicines
		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("medicines", allStaff);

		return renderer.render();
	}
}
