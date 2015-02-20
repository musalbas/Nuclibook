package nuclibook.routes;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.models.*;
import spark.Request;
import spark.Response;

import java.sql.Date;

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
		if (entityType.equals("camera")) {
			entity = createUpdateCamera(entityId, request);
			dbClass = Camera.class;
		}
		if (entityType.equals("medicine")) {
			entity = createUpdateMedicine(entityId, request);
			dbClass = Medicine.class;
		}
		if (entityType.equals("patient")) {
			entity = createUpdatePatient(entityId, request);
			dbClass = Patient.class;
		}
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

	private Camera createUpdateCamera(int entityId, Request request) {
		// create and set ID
		Camera entity;
		if (createNew) {
			entity = new Camera();
		} else {
			entity = AbstractEntityUtils.getEntityById(Camera.class, entityId);
		}

		// name
		entity.setRoomNumber(request.queryParams("room-number"));

		// type
		CameraType type = CameraTypeUtils.getCameraType(request.queryParams("camera-type-id"));
		entity.setType(type);

		return entity;
	}

	private Medicine createUpdateMedicine(int entityId, Request request) {
		// create and set ID
		Medicine entity;
		if (createNew) {
			entity = new Medicine();
		} else {
			entity = AbstractEntityUtils.getEntityById(Medicine.class, entityId);
		}

		// name
		entity.setName(request.queryParams("name"));

		// order time
		try {
			entity.setOrderTime(Integer.parseInt(request.queryParams("order-time")));
		} catch (NumberFormatException e) {
			entity.setOrderTime(0);
		}

		return entity;
	}

	private Patient createUpdatePatient(int entityId, Request request) {
		// create and set ID
		Patient entity;
		if (createNew) {
			entity = new Patient();
		} else {
			entity = AbstractEntityUtils.getEntityById(Patient.class, entityId);
		}

		// name
		entity.setName(request.queryParams("name"));

		// hospital number
		try {
			entity.setHospitalNumber(Integer.parseInt(request.queryParams("hospital-number")));
		} catch (NumberFormatException e) {
			entity.setHospitalNumber(0);
		}

		// dob
		entity.setDateOfBirth(Date.valueOf(request.queryParams("date-of-birth")));

		return entity;
	}

	private Staff createUpdateStaff(int entityId, Request request) {
		// create and set ID
		Staff entity;
		if (createNew) {
			entity = new Staff();
		} else {
			entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
		}

		// names
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

		// role
		StaffRole role = StaffRoleUtils.getStaffRole(request.queryParams("role-id"));
		entity.setRole(role);

		return entity;
	}
}
