package nuclibook.routes;

import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class StaffRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return null;
	}

	public Object handleGet() {
		HtmlRenderer renderer = new HtmlRenderer("staff.html");

		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff();
		renderer.setCollection("staff", allStaff);

		return renderer.render();
	}
}
