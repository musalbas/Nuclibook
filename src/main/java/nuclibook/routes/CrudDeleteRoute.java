package nuclibook.routes;

import spark.Request;
import spark.Response;

public class CrudDeleteRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// get request info
		String entityType = request.queryParams("entity-type");
		String entityId = request.queryParams("entity-id");

		System.out.println("DELETE");
		System.out.println("Entity type: " + entityType);
		System.out.println("Entity ID: " + entityId);

		return null;
	}
}
