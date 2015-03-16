package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
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

		switch (entityType) {
			case "camera": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_CAMERA, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				Camera entity = AbstractEntityUtils.getEntityById(Camera.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(Camera.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_CAMERA, entity.getId());
				return "okay";
			}

			case "camera-type": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_CAMERA_TYPE, entityId, "Failed as user does not have permissions for this action");
                    return "no_permission";
				}

				CameraType entity = AbstractEntityUtils.getEntityById(CameraType.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(CameraType.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_CAMERA_TYPE, entity.getId());
				return "okay";
			}

			case "patient": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_PATIENT, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				Patient entity = AbstractEntityUtils.getEntityById(Patient.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(Patient.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_PATIENT, entity.getId());
				return "okay";
			}

			case "staff": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_STAFF, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				Staff entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(Staff.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_STAFF, entity.getId());
				return "okay";
			}

			case "staff-absence": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_ABSENCES)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_STAFF_ABSENCE, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				StaffAbsence entity = AbstractEntityUtils.getEntityById(StaffAbsence.class, entityId);
				AbstractEntityUtils.deleteEntity(StaffAbsence.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_STAFF_ABSENCE, entity.getId());
				return "okay";
			}

			case "staff-availability": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_AVAILABILITIES)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_STAFF_AVAILABILITY, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				StaffAvailability entity = AbstractEntityUtils.getEntityById(StaffAvailability.class, entityId);
				AbstractEntityUtils.deleteEntity(StaffAvailability.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_STAFF_AVAILABILITY, entity.getId());
				return "okay";
			}

			case "staff-role": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_ROLES)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_STAFF_ROLE, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				StaffRole entity = AbstractEntityUtils.getEntityById(StaffRole.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(StaffRole.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_STAFF_ROLE, entity.getId());
				return "okay";
			}

			case "therapy": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_THERAPIES)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_THERAPY, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				Therapy entity = AbstractEntityUtils.getEntityById(Therapy.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(Therapy.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_THERAPY, entity.getId());
				return "okay";
			}

			case "tracer": {
				// permission
				if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_TRACERS)) {
                    ActionLogger.logAction(ActionLogger.ATTEMPT_DELETE_TRACER, entityId, "Failed as user does not have permissions for this action");
					return "no_permission";
				}

				Tracer entity = AbstractEntityUtils.getEntityById(Tracer.class, entityId);
				entity.setEnabled(false);
				AbstractEntityUtils.updateEntity(Tracer.class, entity);
                ActionLogger.logAction(ActionLogger.DELETE_TRACER, entity.getId());
				return "okay";
			}
		}

		// fail safe
		return "error";
	}
}
