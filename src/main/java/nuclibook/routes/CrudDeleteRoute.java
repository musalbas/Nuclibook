package nuclibook.routes;

import javafx.util.Pair;
import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.SecurityUtils;
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
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
                return "no_permission";
            }

			Camera entity = AbstractEntityUtils.getEntityById(Camera.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Camera.class, entity);
		}

		// delete camera type
		if (entityType.equals("camera-type")) {
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
                return "no_permission";
            }

			CameraType entity = AbstractEntityUtils.getEntityById(CameraType.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(CameraType.class, entity);
		}

		// delete medicine
		if (entityType.equals("medicine")) {
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_MEDICINES)) {
                return "no_permission";
            }

			Medicine entity = AbstractEntityUtils.getEntityById(Medicine.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Medicine.class, entity);
		}

		// delete patient
		if (entityType.equals("patient")) {
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
                return "no_permission";
            }

			Patient entity = AbstractEntityUtils.getEntityById(Patient.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Patient.class, entity);
		}

		// delete staff
		if (entityType.equals("staff")) {
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF)) {
                return "no_permission";
            }

			Staff entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Staff.class, entity);
		}

		// delete staff role
		if (entityType.equals("staff-role")) {
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_ROLES)) {
                return "no_permission";
            }

			StaffRole entity = AbstractEntityUtils.getEntityById(StaffRole.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(StaffRole.class, entity);
		}

		// delete therapy
		if (entityType.equals("therapy")) {
            // permission
            if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().hasPermission(P.EDIT_THERAPIES)) {
                return "no_permission";
            }

			Therapy entity = AbstractEntityUtils.getEntityById(Therapy.class, entityId);
			entity.setEnabled(false);
			AbstractEntityUtils.updateEntity(Therapy.class, entity);
		}

		return "okay";
	}
}
