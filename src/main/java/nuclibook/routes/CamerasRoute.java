package nuclibook.routes;

import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class CamerasRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// start renderer
		HtmlRenderer renderer = new HtmlRenderer("cameras.html");

		// TODO: change to cameras
		// get staff and add to renderer
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		renderer.setCollection("cameras", allStaff);

		return renderer.render();
	}
}
