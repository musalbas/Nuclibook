package nuclibook.routes;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.models.*;
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

		// delete camera
		if (entityType.equals("camera")) {
			Camera entity = AbstractEntityUtils.getEntityById(Camera.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Camera.class, entity);
		}

		// delete medicine
		if (entityType.equals("medicine")) {
			Medicine entity = AbstractEntityUtils.getEntityById(Medicine.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Medicine.class, entity);
		}

		// delete patient
		if (entityType.equals("patient")) {
			Patient entity = AbstractEntityUtils.getEntityById(Patient.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Patient.class, entity);
		}

		// delete staff
		if (entityType.equals("staff")) {
			Staff entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Staff.class, entity);
		}

		// delete staff role
		if (entityType.equals("staff-role")) {
			StaffRole entity = AbstractEntityUtils.getEntityById(StaffRole.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(StaffRole.class, entity);
		}

		// delete therapy
		if (entityType.equals("therapy")) {
			Therapy entity = AbstractEntityUtils.getEntityById(Therapy.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Therapy.class, entity);
		}

		return "okay";
	}
}
