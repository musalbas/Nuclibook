package nuclibook.routes;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;

public class CrudDeleteRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// get request info
		String entityType = request.queryParams("entity-type");
		int entityId = 0;
		try {
			entityId = Integer.parseInt(request.queryParams("entity-id"));
		} catch (NumberFormatException e) {
			// leave it at zero
		}

		// delete
		if (entityType.equals("staff")) {
			Staff entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Staff.class, entity);
		}

		return "okay";
	}
}
