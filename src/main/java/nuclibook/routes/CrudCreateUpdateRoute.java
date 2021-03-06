package nuclibook.routes;

import javafx.util.Pair;
import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is called whenever the user wants to update the information about resources or about staff.
 */
public class CrudCreateUpdateRoute extends DefaultRoute {

	/**
	 * Boolean value that checks if the entity needs to be created.
	 */
	private boolean createNew = false;

	/**
	 * Handles user's request.
	 *
	 * @param request  Information sent by the client
	 * @param response Information sent to the client
	 * @return the Status of the user's request
	 * @throws Exception if something goes wrong, for example, loss of connection with a server
	 */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

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
		Pair<Status, Object> entityPair = null;
		Class dbClass = null;
		Integer action = null;

		// build new/updated entity
		switch (entityType) {
			case "camera":
				entityPair = createUpdateCamera(entityId, request);
				dbClass = Camera.class;
				action = (entityId == 0) ? ActionLogger.CREATE_CAMERA : ActionLogger.UPDATE_CAMERA;
				break;

			case "camera-type":
				entityPair = createUpdateCameraType(entityId, request);
				dbClass = CameraType.class;
				action = (entityId == 0) ? ActionLogger.CREATE_CAMERA_TYPE : ActionLogger.UPDATE_CAMERA_TYPE;
				break;

			case "generic-event":
				entityPair = createUpdateGenericEvent(entityId, request);
				dbClass = GenericEvent.class;
				action = (entityId == 0) ? ActionLogger.CREATE_GENERIC_EVENT : ActionLogger.UPDATE_GENERIC_EVENT;
				break;

			case "tracer":
				entityPair = createUpdateTracer(entityId, request);
				dbClass = Tracer.class;
				action = (entityId == 0) ? ActionLogger.CREATE_TRACER : ActionLogger.UPDATE_TRACER;
				break;

			case "patient":
				entityPair = createUpdatePatient(entityId, request);
				dbClass = Patient.class;
				action = (entityId == 0) ? ActionLogger.CREATE_PATIENT : ActionLogger.UPDATE_PATIENT;
				break;

			case "staff":
				entityPair = createUpdateStaff(entityId, request);
				dbClass = Staff.class;
				action = (entityId == 0) ? ActionLogger.CREATE_STAFF : ActionLogger.UPDATE_STAFF;
				break;

			case "staff-absence":
				entityPair = createUpdateStaffAbsence(entityId, request);
				dbClass = StaffAbsence.class;
				action = (entityId == 0) ? ActionLogger.CREATE_STAFF_ABSENCE : ActionLogger.UPDATE_STAFF_ABSENCE;
				break;

			case "staff-availability":
				entityPair = createUpdateStaffAvailability(entityId, request);
				dbClass = StaffAvailability.class;
				action = (entityId == 0) ? ActionLogger.CREATE_STAFF_AVAILABILITY : ActionLogger.UPDATE_STAFF_AVAILABILITY;
				break;

			case "staff-password-change":
				entityPair = createUpdateStaffPassword(request);
				dbClass = Staff.class;
				action = ActionLogger.UPDATE_STAFF_PASSWORD;
				break;

			case "staff-role":
				entityPair = createUpdateStaffRole(entityId, request);
				dbClass = StaffRole.class;
				action = (entityId == 0) ? ActionLogger.CREATE_STAFF_ROLE : ActionLogger.UPDATE_STAFF_ROLE;
				break;

			case "therapy":
				entityPair = createUpdateTherapy(entityId, request);
				dbClass = Therapy.class;
				action = (entityId == 0) ? ActionLogger.CREATE_THERAPY : ActionLogger.UPDATE_THERAPY;
				break;

			default:
				// un-matched type
				return "error";
		}

		// some entities handle creation internally
		if (entityPair == null) {
			return "okay";
		}

		// save/update
		if (entityPair.getKey() == Status.OK) {
			if (entityPair.getValue() != null) {
				if (createNew) {
					//need to obtain object id after it is created
					switch (entityType) {
						case "camera":
							Camera camera = (Camera) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, camera.getId());
							break;

						case "camera-type":
							CameraType cameraType = (CameraType) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, cameraType.getId());
							break;

						case "generic-event":
							GenericEvent genericEvent = (GenericEvent) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, genericEvent.getId());
							break;

						case "tracer":
							Tracer tracer = (Tracer) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, tracer.getId());
							break;

						case "patient":
							Patient patient = (Patient) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, patient.getId());
							break;

						case "staff":
							Staff staff = (Staff) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, staff.getId());
							break;

						case "staff-absence":
							StaffAbsence staffAbsence = (StaffAbsence) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, staffAbsence.getId());
							break;

						case "staff-availability":
							StaffAvailability staffAvailability = (StaffAvailability) AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
							ActionLogger.logAction(user, action, staffAvailability.getId());
							break;
					}
				} else {
					AbstractEntityUtils.updateEntity(dbClass, entityPair.getValue());
					if (entityType.equals("staff-password-change")) {
						ActionLogger.logAction(user, action, user.getId());
					} else {
						ActionLogger.logAction(user, action, entityId);
					}
				}
			}

			// anything to do afterwards?
			if (entityType.equals("staff-role") || entityType.equals("staff")) {
				user.refreshPermissions();
			}

			return "okay";
		}

		// failed validation
		if (entityPair.getKey() == Status.FAILED_VALIDATION) {
			return "failed_validation";
		}

		// no permission
		if (entityPair.getKey() == Status.NO_PERMISSION) {
			return "no_permission";
		}

		// custom error
		if (entityPair.getKey() == Status.CUSTOM_ERROR) {
			return "CUSTOM:" + entityPair.getValue();
		}

		// fail safe
		return "error";
	}

	/**
	 * Creates new Camera or updates existing.
	 *
	 * @param entityId Entity ID.
	 * @param request  Information request sent by the client.
	 * @return entity with the the request status.
	 */
	private Pair<Status, Object> createUpdateCamera(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_CAMERAS)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_CAMERA : ActionLogger.ATTEMPT_UPDATE_CAMERA;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("room-number").length() > 32
				|| !request.queryParams("room-number").matches("[a-zA-Z0-9\\-\\. ]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		Camera entity;
		if (createNew) {
			entity = new Camera();
		} else {
			entity = AbstractEntityUtils.getEntityById(Camera.class, entityId);
		}

		// room-number
		entity.setRoomNumber(request.queryParams("room-number"));

		// type
		CameraType type = CameraTypeUtils.getCameraType(request.queryParams("camera-type-id"));
		entity.setType(type);

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new CameraType or updates existing.
	 *
	 * @param entityId Entity ID.
	 * @param request  Information request sent by the client.
	 * @return entity with the the request status.
	 */
	private Pair<Status, Object> createUpdateCameraType(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_CAMERAS)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_CAMERA_TYPE : ActionLogger.ATTEMPT_UPDATE_CAMERA_TYPE;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("label").length() > 64
				|| !request.queryParams("label").matches("[a-zA-Z\\-\\. ]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		CameraType entity;
		if (createNew) {
			entity = new CameraType();
		} else {
			entity = AbstractEntityUtils.getEntityById(CameraType.class, entityId);
		}

		// label
		entity.setLabel(request.queryParams("label"));

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Generic event or updates existing.
	 *
	 * @param entityId Entity ID.
	 * @param request  Information request sent by the client.
	 * @return entity with the the request status.
	 */
	private Pair<Status, Object> createUpdateGenericEvent(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_GENERIC_EVENTS)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_GENERIC_EVENT : ActionLogger.ATTEMPT_UPDATE_GENERIC_EVENT;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		String title = request.queryParams("title");
		String description = request.queryParams("description") == null ? "" : request.queryParams("description");
		if (title.trim().length() < 1 || title.length() > 64 || description.length() > 255
				|| !title.matches("[a-zA-Z|0-9\\-\\.' ]+") || !description.matches("[a-zA-Z|0-9\\-\\.' ]+"))
			return new Pair<>(Status.FAILED_VALIDATION, null);

		DateTime from;
		DateTime to;
		try {
			// attempt to parse the dates
			from = new DateTime(request.queryParams("from-date") + "T" + request.queryParams("from-time") + ":00");
			to = new DateTime(request.queryParams("to-date") + "T" + request.queryParams("to-time") + ":00");
			if (from.isAfter(to)) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		GenericEvent entity;
		if (createNew) {
			entity = new GenericEvent();
		} else {
			entity = AbstractEntityUtils.getEntityById(GenericEvent.class, entityId);
		}

		//title and description
		entity.setTitle(title);
		entity.setDescription(description);
		// dates
		entity.setFrom(from);
		entity.setTo(to);

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Patient or updates existing.
	 *
	 * @param entityId Entity ID.
	 * @param request  Information request sent by the client.
	 * @return entity with the the request status.
	 */
	private Pair<Status, Object> createUpdatePatient(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_PATIENTS)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_PATIENT : ActionLogger.ATTEMPT_UPDATE_PATIENT;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
				|| !request.queryParams("hospital-number").matches("[a-zA-Z0-9\\-]{1,64}")
				|| !request.queryParams("nhs-number").matches("[a-zA-Z0-9\\-]{1,64}")
				|| !request.queryParams("date-of-birth").matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

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
		entity.setHospitalNumber(request.queryParams("hospital-number"));

		// nhs number
		entity.setNhsNumber(request.queryParams("nhs-number"));

		// sex
		if (request.queryParams("sex").equals("Male")) {
			entity.setSex(Patient.Sex.MALE);
		} else {
			entity.setSex(Patient.Sex.FEMALE);
		}

		// dob
		entity.setDateOfBirth(new DateTime(request.queryParams("date-of-birth")));

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Staff or updates existing.
	 *
	 * @param entityId Entity ID
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateStaff(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_STAFF)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_STAFF : ActionLogger.ATTEMPT_UPDATE_STAFF;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| request.queryParams("username").length() > 64
				|| !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
				|| !request.queryParams("username").matches("[a-zA-Z0-9]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		Staff entity;
		if (createNew) {
			entity = new Staff();
		} else {
			entity = AbstractEntityUtils.getEntityById(Staff.class, entityId);
		}

		// check if staff username is taken and the request is not updating a user
		if (createNew || !request.queryParams("username").equals(entity.getUsername())) {
			System.out.println("Comparing '" + request.queryParams("username") + "' vs '" + user.getUsername() + "'");
			if (StaffUtils.usernameExists(request.queryParams("username"))) {
				ActionLogger.logAction(user, ActionLogger.ATTEMPT_UPDATE_STAFF, entityId, "Failed as the chosen username already exists");
				return new Pair<>(Status.CUSTOM_ERROR, "Username has been taken");
			}
		}

		// names
		entity.setName(request.queryParams("name"));
		entity.setUsername(request.queryParams("username"));

		// password
		if (request.queryParams("password") != null && !request.queryParams("password").equals("") && request.queryParams("password").length() > 0) {
			// password strength validation
			if (!createNew && !user.hasPermission(P.EDIT_STAFF_PASSWORD)) {
				ActionLogger.logAction(user, ActionLogger.ATTEMPT_UPDATE_STAFF_PASSWORD, entityId, "Failed as the user does not have permissions to edit other users' passwords");
				return new Pair<>(Status.NO_PERMISSION, null);
			}

			String passwordError;
			try {
				passwordError = SecurityUtils.validateNewPassword(entity, request.queryParams("password"));
			} catch (CannotHashPasswordException e) {
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}
			if (passwordError != null) {
				Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_STAFF_PASSWORD : ActionLogger.ATTEMPT_UPDATE_STAFF_PASSWORD;
				ActionLogger.logAction(user, action, entityId, "Failed as the following error had occurred: " + passwordError);
				return new Pair<>(Status.CUSTOM_ERROR, passwordError);
			}

			try {
				entity.setPassword(request.queryParams("password"));
			} catch (CannotHashPasswordException e) {
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}
		}

		// role
		StaffRole role = StaffRoleUtils.getStaffRole(request.queryParams("role-id"));
		entity.setRole(role);

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Staff Absence or updates existing.
	 *
	 * @param entityId Entity ID
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateStaffAbsence(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_STAFF_ABSENCES)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_STAFF_ABSENCE : ActionLogger.ATTEMPT_UPDATE_STAFF_ABSENCE;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		DateTime from;
		DateTime to;
		try {
			// attempt to parse the dates
			from = new DateTime(request.queryParams("from-date") + "T" + request.queryParams("from-time") + ":00");
			to = new DateTime(request.queryParams("to-date") + "T" + request.queryParams("to-time") + ":00");
			if (from.isAfter(to)) {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		StaffAbsence entity;
		if (createNew) {
			entity = new StaffAbsence();
		} else {
			entity = AbstractEntityUtils.getEntityById(StaffAbsence.class, entityId);
		}

		// dates
		entity.setFrom(from);
		entity.setTo(to);

		// staff
		Staff staff = StaffUtils.getStaff(request.queryParams("staff-id"));
		entity.setStaff(staff);

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Staff availability or updates existing.
	 *
	 * @param entityId Entity ID
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateStaffAvailability(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_STAFF_AVAILABILITIES)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_STAFF_AVAILABILITY : ActionLogger.ATTEMPT_UPDATE_STAFF_AVAILABILITY;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		String dayOfWeekString = request.queryParams("day-of-week");
		int dayOfWeek;
		String startTime = request.queryParams("start-time");
		String endTime = request.queryParams("end-time");
		int startTimeSecondsPastMidnight;
		int endTimeSecondsPastMidnight;
		try {
			dayOfWeek = Integer.parseInt(dayOfWeekString);
			startTimeSecondsPastMidnight = (Integer.parseInt(startTime.split(":")[0]) * 3600) + (Integer.parseInt(startTime.split(":")[1]) * 60);
			endTimeSecondsPastMidnight = (Integer.parseInt(endTime.split(":")[0]) * 3600) + (Integer.parseInt(endTime.split(":")[1]) * 60);
		} catch (NumberFormatException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		StaffAvailability entity;
		if (createNew) {
			entity = new StaffAvailability();
		} else {
			entity = AbstractEntityUtils.getEntityById(StaffAvailability.class, entityId);
		}

		// dates
		try {
			entity.setDay(dayOfWeek);
			entity.setStartTime(new TimeOfDay(startTimeSecondsPastMidnight));
			entity.setEndTime(new TimeOfDay(endTimeSecondsPastMidnight));
		} catch (InvalidTimeOfDayException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// staff
		Staff staff = StaffUtils.getStaff(request.queryParams("staff-id"));
		entity.setStaff(staff);

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Staff password or updates existing.
	 *
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateStaffPassword(Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// this is not new
		createNew = false;

		// get current user
		Staff entity = user;

		// check against current password
		try {
			if (!entity.checkPassword(request.queryParams("password_old"))) {
				ActionLogger.logAction(user, ActionLogger.ATTEMPT_UPDATE_STAFF_PASSWORD, entity.getId(), "Failed as the the user entered an incorrect password");
				return new Pair<>(Status.CUSTOM_ERROR, "Your current password was incorrect");
			}
		} catch (CannotHashPasswordException e) {
			ActionLogger.logAction(user, ActionLogger.ATTEMPT_UPDATE_STAFF_PASSWORD, entity.getId(), "Failed as the the user entered an incorrect password");
			return new Pair<>(Status.CUSTOM_ERROR, "Your current password was incorrect");
		}

		// password strength validation
		String passwordError;
		try {
			passwordError = SecurityUtils.validateNewPassword(entity, request.queryParams("password"));
		} catch (CannotHashPasswordException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}
		if (passwordError != null) {
			ActionLogger.logAction(user, ActionLogger.ATTEMPT_UPDATE_STAFF_PASSWORD, entity.getId(), "Failed as the following error had occurred: " + passwordError);
			return new Pair<>(Status.CUSTOM_ERROR, passwordError);
		}

		// password check validation
		if (!request.queryParams("password").equals(request.queryParams("password_check"))) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// change current staff password
		try {
			entity.setPassword(request.queryParams("password"));
		} catch (CannotHashPasswordException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Staff role or updates existing.
	 *
	 * @param entityId Entity ID
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateStaffRole(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_STAFF_ROLES)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_STAFF_ROLE : ActionLogger.ATTEMPT_UPDATE_STAFF_ROLE;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("label").length() > 32
				|| !request.queryParams("label").matches("[a-zA-Z0-9\\-\\.' ]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		//check if no permissions were sent
		Set<String> keys = request.queryMap().toMap().keySet();
		Iterator<String> iterator = keys.iterator();
		int permissionsSent = 0;

		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.startsWith("permission-")) {
				permissionsSent++;
			}
		}

		if (permissionsSent < 1) {
			return new Pair<>(Status.CUSTOM_ERROR, "Please select at least one permission");
		}

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
			StaffRole staffRole = AbstractEntityUtils.createEntity(StaffRole.class, entity);
			ActionLogger.logAction(user, ActionLogger.CREATE_STAFF_ROLE, staffRole.getId());
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
		return createNew ? null : new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Therapy or updates existing.
	 *
	 * @param entityId Entity ID
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateTherapy(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_THERAPIES)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_THERAPY : ActionLogger.ATTEMPT_UPDATE_THERAPY;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| request.queryParams("tracer-dose").length() > 32
				|| !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
				|| !request.queryParams("tracer-dose").matches("[a-zA-Z0-9\\-\\. ]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		Therapy entity;
		if (createNew) {
			entity = new Therapy();
		} else {
			entity = AbstractEntityUtils.getEntityById(Therapy.class, entityId);
		}

		// name
		entity.setName(request.queryParams("name"));

		// tracer required
		Tracer tracer = TracerUtils.getTracer(request.queryParams("tracer-required-id"));
		entity.setTracerRequired(tracer);

		// tracer dose
		entity.setTracerDose(request.queryParams("tracer-dose"));

		// if it's new, we'll save it here so that foreign collections can be added properly
		Therapy therapy = null; // save for action logging later after all validation has passed
		if (createNew) {
			therapy = AbstractEntityUtils.createEntity(Therapy.class, entity);
		}

		// clear current booking pattern
		List<BookingPatternSection> currentBookingPattern = entity.getBookingPatternSections();
		if (currentBookingPattern != null) {
			for (BookingPatternSection bps : currentBookingPattern) {
				AbstractEntityUtils.deleteEntity(BookingPatternSection.class, bps);
			}
		}

		// booking pattern
		BookingPatternSection bps;
		Map<String, String[]> paramMap = request.queryMap().toMap();
		String key, value, valueA, valueB;
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			// get key value
			key = entry.getKey();

			// is this a patient question?
			if (!(key.startsWith("booking-section-") && key.endsWith("a"))) {
				continue;
			}

			// get values
			int entryNumber;
			try {
				entryNumber = Integer.parseInt(key.substring(16, key.length() - 1));
			} catch (NumberFormatException e) {
				continue;
			}
			valueA = request.queryParams("booking-section-" + entryNumber + "a");
			valueB = request.queryParams("booking-section-" + entryNumber + "b").replace(" ", "");

			// skip blanks
			if (valueB.length() == 0) {
				continue;
			}

			// validation
			if (!valueA.equals("busy") && !valueA.equals("wait")) {
				AbstractEntityUtils.deleteEntity(Therapy.class, therapy);
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}
			if (!valueB.matches("[0-9]+(\\-[0-9]+)?")) {
				AbstractEntityUtils.deleteEntity(Therapy.class, therapy);
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}

			if (valueB.contains("-")) {
				String[] times = valueB.split("-");
				if (Integer.parseInt(times[1]) < Integer.parseInt(times[0])) {
					return new Pair<>(Status.FAILED_VALIDATION, null);
				}
			}

			// add booking pattern sections to the entity
			bps = new BookingPatternSection();
			bps.setTherapy(entity);
			bps.setBusy(valueA.equals("busy"));
			bps.setSequence(entryNumber);
			if (valueB.contains("-")) {
				String[] valueBParts = valueB.split("\\-");
				bps.setMinLength(Integer.parseInt(valueBParts[0]));
				bps.setMaxLength(Integer.parseInt(valueBParts[1]));
			} else {
				bps.setMinLength(Integer.parseInt(valueB));
				bps.setMaxLength(Integer.parseInt(valueB));
			}
			AbstractEntityUtils.createEntity(BookingPatternSection.class, bps);
		}

		// camera types
		entity.clearCameraTypes();
		CameraType ct;
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			// get key value
			key = entry.getKey();

			// is this a camera type
			if (!key.startsWith("camera-type-")) {
				continue;
			}

			// get permission
			key = key.substring(12);
			ct = CameraTypeUtils.getCameraType(key);
			if (ct != null) entity.addCameraType(ct);
		}

		// clear current questions
		List<PatientQuestion> currentQuestions = entity.getPatientQuestions();
		if (currentQuestions != null) {
			for (PatientQuestion pq : currentQuestions) {
				AbstractEntityUtils.deleteEntity(PatientQuestion.class, pq);
			}
		}

		// patient questions
		PatientQuestion pq;
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			// get key value
			key = entry.getKey();

			// is this a patient question?
			if (!key.startsWith("patient-question-")) {
				continue;
			}

			// get value and check length
			value = entry.getValue()[0];
			if (value.length() == 0) {
				continue;
			}
			if (value.length() > 256) {
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}

			// get question number
			int questionNumber;
			try {
				questionNumber = Integer.parseInt(key.substring(17));
			} catch (NumberFormatException e) {
				continue;
			}

			// add questions to the entity
			pq = new PatientQuestion();
			pq.setDescription(entry.getValue()[0]);
			pq.setTherapy(entity);
			pq.setSequence(questionNumber);
			AbstractEntityUtils.createEntity(PatientQuestion.class, pq);
		}

		// all validation has passed, we can now log the action
		ActionLogger.logAction(user, ActionLogger.CREATE_THERAPY, entity.getId());

		// don't let it be created again if it's new
		return createNew ? null : new Pair<>(Status.OK, entity);
	}

	/**
	 * Creates new Tracer or updates existing.
	 *
	 * @param entityId Entity ID
	 * @param request  Information request sent by the client
	 * @return entity with the the request status
	 */
	private Pair<Status, Object> createUpdateTracer(int entityId, Request request) {
		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// permission
		if (user == null || !user.hasPermission(P.EDIT_TRACERS)) {
			Integer action = (createNew) ? ActionLogger.ATTEMPT_CREATE_TRACER : ActionLogger.ATTEMPT_UPDATE_TRACER;
			ActionLogger.logAction(user, action, entityId, "Failed as user does not have permissions for this action");
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| !request.queryParams("name").matches("[0-9a-zA-Z\\-\\.' ]+")
				|| !request.queryParams("order-time").matches("[0-9]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// create and set ID
		Tracer entity;
		if (createNew) {
			entity = new Tracer();
		} else {
			entity = AbstractEntityUtils.getEntityById(Tracer.class, entityId);
		}

		// name
		entity.setName(request.queryParams("name"));

		// order time
		try {
			entity.setOrderTime(Integer.parseInt(request.queryParams("order-time")));
		} catch (NumberFormatException e) {
			entity.setOrderTime(0);
		}

		return new Pair<>(Status.OK, entity);
	}

	/**
	 * Represents the four possible values that status of the user's request may have.
	 */
	private enum Status {
		OK,
		FAILED_VALIDATION,
		NO_PERMISSION,
		CUSTOM_ERROR
	}
}
