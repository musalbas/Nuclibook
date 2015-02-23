package nuclibook.routes;

import nuclibook.entity_utils.*;
import nuclibook.models.*;
import spark.Request;
import spark.Response;

import java.sql.Date;
import java.util.Map;

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
		if (entityType.equals("camera-type")) {
			entity = createUpdateCameraType(entityId, request);
			dbClass = CameraType.class;
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
		if (entityType.equals("staff-role")) {
			entity = createUpdateStaffRole(entityId, request);
			dbClass = StaffRole.class;
		}
		if (entityType.equals("therapy")) {
			entity = createUpdateTherapy(entityId, request);
			dbClass = Therapy.class;
		}

		// save/update
		if (entity != null) {
			if (createNew) {
				AbstractEntityUtils.createEntity(dbClass, entity);
			} else {
				AbstractEntityUtils.updateEntity(dbClass, entity);
			}
		}

		// anything to do afterwards?
		if (entityType.equals("staff-role") || entityType.equals("staff")) {
			SecurityUtils.getCurrentUser().refreshPermissions();
		}

		// TODO: actually monitor status
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

	private CameraType createUpdateCameraType(int entityId, Request request) {
		// create and set ID
		CameraType entity;
		if (createNew) {
			entity = new CameraType();
		} else {
			entity = AbstractEntityUtils.getEntityById(CameraType.class, entityId);
		}

		// label
		entity.setLabel(request.queryParams("label"));

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

	private StaffRole createUpdateStaffRole(int entityId, Request request) {
		// create and set ID
		StaffRole entity;
		if (createNew) {
			entity = new StaffRole();
		} else {
			entity = AbstractEntityUtils.getEntityById(StaffRole.class, entityId);
		}

		// label
		entity.setLabel(request.queryParams("label"));

		// if it's new, we'll save it here so that the permissions can be added properly
		if (createNew) {
			AbstractEntityUtils.createEntity(StaffRole.class, entity);
		}

		// permissions
		entity.clearPermissions();
		Map<String, String[]> paramMap = request.queryMap().toMap();
		String key;
		Permission p;
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			// get key value
			key = entry.getKey();

			// is this a permission?
			if (!key.startsWith("permission-")) {
				continue;
			}

			// get permission
			key = key.substring(11);
			p = PermissionUtils.getPermission(key);
			if (p != null) entity.addPermission(p);
		}

		// don't let it be created again if it's new
		return createNew ? null : entity;
	}

	private Therapy createUpdateTherapy(int entityId, Request request) {
		// create and set ID
		Therapy entity;
		if (createNew) {
			entity = new Therapy();
		} else {
			entity = AbstractEntityUtils.getEntityById(Therapy.class, entityId);
		}

		// name
		entity.setName(request.queryParams("name"));

		// hospital number
		try {
			entity.setDuration(Integer.parseInt(request.queryParams("default-duration")));
		} catch (NumberFormatException e) {
			entity.setDuration(0);
		}

		// medicine required
		Medicine medicine = MedicineUtils.getMedicine(request.queryParams("medicine-required-id"));
		entity.setMedicineRequired(medicine);

		// name
		entity.setMedicineDose(request.queryParams("medicine-dose"));

		// camera type
		CameraType type = CameraTypeUtils.getCameraType(request.queryParams("camera-type-id"));
		entity.setCameraTypeRequired(type);

		return entity;
	}
}
