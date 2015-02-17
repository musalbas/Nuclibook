package nuclibook.routes;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;

public class CrudDeleteRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// get request info
		String entityType = request.queryParams("entity-type");
		String entityId = request.queryParams("entity-id");

		// delete
		// TODO: replace with status change
		if (entityType.equals("staff")) {
			AbstractEntityUtils.deleteEntityById(Staff.class, Integer.parseInt(entityId));
		}

		return "okay";
	}
}
