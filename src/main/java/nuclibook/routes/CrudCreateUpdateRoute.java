package nuclibook.routes;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import spark.Request;
import spark.Response;

public class CrudCreateUpdateRoute extends DefaultRoute {

	private boolean createNew = false;

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
		createNew = entityId == 0;

		// entity to work on
		Object entity = null;
		Class dbClass = null;

		// build new/updated entity
		if (entityType.equals("staff")) {
			entity = createUpdateStaff(entityId, request);
			dbClass = Staff.class;
		}

		// save/update
		if (entity != null) {
			if (createNew) {
				AbstractEntityUtils.createEntity(dbClass, entity);
			} else {
				AbstractEntityUtils.updateEntity(dbClass, entity);
			}
		}

		return "okay";
	}

	private Staff createUpdateStaff(int entityID, Request request) {
		// create and set ID
		Staff entity;
		if (createNew) {
			entity = new Staff();
		} else {
			entity = AbstractEntityUtils.getEntityById(Staff.class, entityID);
		}

		// basics
		entity.setName(request.queryParams("name"));
		entity.setUsername(request.queryParams("username"));

		// password
		if (request.queryParams("password") != null && request.queryParams("password").length() > 0) {
			try {
				entity.setPassword(request.queryParams("password"));
			} catch (CannotHashPasswordException e) {
				e.printStackTrace();
			}
		}

		// TODO: role

		return entity;
	}
}
