package nuclibook.routes;

import javafx.util.Pair;
import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.List;
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
		Pair<Status, Object> entityPair = null;
		Class dbClass = null;

		// build new/updated entity
		switch (entityType) {
			case "camera":
				entityPair = createUpdateCamera(entityId, request);
				dbClass = Camera.class;
				break;

			case "camera-type":
				entityPair = createUpdateCameraType(entityId, request);
				dbClass = CameraType.class;
				break;

			case "tracer":
				entityPair = createUpdateTracer(entityId, request);
				dbClass = Tracer.class;
				break;

			case "patient":
				entityPair = createUpdatePatient(entityId, request);
				dbClass = Patient.class;
				break;

			case "staff":
				entityPair = createUpdateStaff(entityId, request);
				dbClass = Staff.class;
				break;

			case "staff-absence":
				entityPair = createUpdateStaffAbsence(entityId, request);
				dbClass = StaffAbsence.class;
				break;

			case "staff-availability":
				entityPair = createUpdateStaffAvailability(entityId, request);
				dbClass = StaffAvailability.class;
				break;

			case "staff-password-change":
				entityPair = createUpdateStaffPassword(request);
				dbClass = Staff.class;
				break;

			case "staff-role":
				entityPair = createUpdateStaffRole(entityId, request);
				dbClass = StaffRole.class;
				break;

			case "therapy":
				entityPair = createUpdateTherapy(entityId, request);
				dbClass = Therapy.class;
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
					AbstractEntityUtils.createEntity(dbClass, entityPair.getValue());
				} else {
					AbstractEntityUtils.updateEntity(dbClass, entityPair.getValue());
				}
			}

			// anything to do afterwards?
			if (entityType.equals("staff-role") || entityType.equals("staff")) {
				SecurityUtils.getCurrentUser().refreshPermissions();
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

	private Pair<Status, Object> createUpdateCamera(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
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

	private Pair<Status, Object> createUpdateCameraType(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_CAMERAS)) {
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

	private Pair<Status, Object> createUpdatePatient(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| request.queryParams("hospital-number").length() > 64
				|| !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
				|| !request.queryParams("hospital-number").matches("[a-zA-Z0-9\\-]+")
				|| !request.queryParams("year-of-birth").matches("[0-9]{4}")
				|| !request.queryParams("month-of-birth").matches("[0-9]{2}")
				|| !request.queryParams("day-of-birth").matches("[0-9]{1,2}")) {
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

		// dob
		entity.setDateOfBirth(new DateTime(request.queryParams("year-of-birth") + "-" + request.queryParams("month-of-birth") + "-" + request.queryParams("day-of-birth")));

		return new Pair<>(Status.OK, entity);
	}

	private Pair<Status, Object> createUpdateStaff(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF)) {
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| request.queryParams("username").length() > 64
				|| !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
				|| !request.queryParams("username").matches("[a-zA-Z0-9]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// check if staff username is taken
		if (StaffUtils.usernameExists(request.queryParams("username"))) {
			return new Pair<>(Status.CUSTOM_ERROR, "Username has been taken");
		}

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
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}
		}

		// role
		StaffRole role = StaffRoleUtils.getStaffRole(request.queryParams("role-id"));
		entity.setRole(role);

		return new Pair<>(Status.OK, entity);
	}

	private Pair<Status, Object> createUpdateStaffAbsence(int entityId, Request request) {
		// validation
		DateTime from;
		DateTime to;
		try {
			// attempt to parse the dates
			from = new DateTime(request.queryParams("from").replace(" ", "T") + ":00");
			to = new DateTime(request.queryParams("to").replace(" ", "T") + ":00");
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

	private Pair<Status, Object> createUpdateStaffAvailability(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_AVAILABILITIES)) {
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

	private Pair<Status, Object> createUpdateStaffPassword(Request request) {
		// this is not new
		createNew = false;

		// check against current password
		try {
			if (!SecurityUtils.getCurrentUser().checkPassword(request.queryParams("old_password"))) {
				return new Pair<>(Status.CUSTOM_ERROR, "Your current password was incorrect");
			}
		} catch (CannotHashPasswordException e) {
			return new Pair<>(Status.CUSTOM_ERROR, "Your current password was incorrect EXCEPTION");
		}

		// validation
		if (request.queryParams("password").length() < 6
				|| !request.queryParams("password").equals(request.queryParams("password_check"))) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		// change current staff password
		Staff entity = SecurityUtils.getCurrentUser();
		try {
			entity.setPassword(request.queryParams("password"));
		} catch (CannotHashPasswordException e) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
		}

		return new Pair<>(Status.OK, entity);
	}

	private Pair<Status, Object> createUpdateStaffRole(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_STAFF_ROLES)) {
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("label").length() > 32
				|| !request.queryParams("label").matches("[a-zA-Z\\-\\.' ]+")) {
			return new Pair<>(Status.FAILED_VALIDATION, null);
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
		return createNew ? null : new Pair<>(Status.OK, entity);
	}

	private Pair<Status, Object> createUpdateTherapy(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_THERAPIES)) {
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
		if (createNew) {
			AbstractEntityUtils.createEntity(Therapy.class, entity);
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
			if (!valueA.equals("busy") && !valueB.equals("wait")) {
				return new Pair<>(Status.FAILED_VALIDATION, null);
			}
			if (!valueB.matches("[0-9]+(\\-[0-9]+)?")) {
				return new Pair<>(Status.FAILED_VALIDATION, null);
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

		// don't let it be created again if it's new
		return createNew ? null : new Pair<>(Status.OK, entity);
	}

	private Pair<Status, Object> createUpdateTracer(int entityId, Request request) {
		// permission
		if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_TRACERS)) {
			return new Pair<>(Status.NO_PERMISSION, null);
		}

		// validation
		if (request.queryParams("name").length() > 64
				|| !request.queryParams("name").matches("[a-zA-Z\\-\\.' ]+")
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

	private enum Status {
		OK,
		FAILED_VALIDATION,
		NO_PERMISSION,
		CUSTOM_ERROR
	}
}
