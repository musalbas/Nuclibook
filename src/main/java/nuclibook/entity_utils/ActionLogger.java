package nuclibook.entity_utils;

import nuclibook.models.ActionLog;
import nuclibook.models.Staff;
import org.joda.time.DateTime;

/**
 * This class records user logging. Types of actions are stored as static final integers.
 * Logging an action can be done by calling the static method logAction
 */
public class ActionLogger {

	public static final int VIEW_PATIENTS = 1;
	public static final int CREATE_PATIENT = 2;
	public static final int VIEW_PATIENT = 3;
	public static final int UPDATE_PATIENT = 4;
	//TODO missing 5
	public static final int ATTEMPT_VIEW_PATIENTS = 6;
	public static final int ATTEMPT_CREATE_PATIENT = 7;
	public static final int ATTEMPT_VIEW_PATIENT = 8;
	public static final int ATTEMPT_UPDATE_PATIENT = 9;
	public static final int VIEW_TRACERS = 11;
	public static final int CREATE_TRACER = 12;
	public static final int UPDATE_TRACER = 13;
	public static final int DELETE_TRACER = 14;
	public static final int ATTEMPT_VIEW_TRACERS = 15;
	public static final int ATTEMPT_CREATE_TRACER = 16;
	public static final int ATTEMPT_UPDATE_TRACER = 17;
	public static final int ATTEMPT_DELETE_TRACER = 18;
	public static final int CREATE_CAMERA_TYPE = 19;
	public static final int VIEW_CAMERA_TYPES = 20;
	public static final int UPDATE_CAMERA_TYPE = 21;
	public static final int DELETE_CAMERA_TYPE = 22;
	public static final int ATTEMPT_VIEW_CAMERA_TYPES = 23;
	public static final int ATTEMPT_CREATE_CAMERA_TYPE = 24;
	public static final int ATTEMPT_UPDATE_CAMERA_TYPE = 25;
	public static final int ATTEMPT_DELETE_CAMERA_TYPE = 26;
	public static final int CREATE_CAMERA = 27;
	public static final int VIEW_CAMERAS = 28;
	public static final int UPDATE_CAMERA = 29;
	public static final int DELETE_CAMERA = 30;
	public static final int ATTEMPT_VIEW_CAMERAS = 31;
	public static final int ATTEMPT_CREATE_CAMERA = 32;
	public static final int ATTEMPT_UPDATE_CAMERA = 33;
	public static final int ATTEMPT_DELETE_CAMERA = 34;
	public static final int CREATE_THERAPY = 35;
	public static final int VIEW_THERAPIES = 36;
	public static final int UPDATE_THERAPY = 37;
	public static final int DELETE_THERAPY = 38;
	public static final int ATTEMPT_VIEW_THERAPIES = 39;
	public static final int ATTEMPT_CREATE_THERAPY = 40;
	public static final int ATTEMPT_UPDATE_THERAPY = 41;
	public static final int ATTEMPT_DELETE_THERAPY = 42;
	public static final int VIEW_STAFF_ROLES = 43;
	public static final int CREATE_STAFF_ROLE = 44;
	public static final int UPDATE_STAFF_ROLE = 45;
	public static final int DELETE_STAFF_ROLE = 46;
	public static final int ATTEMPT_VIEW_STAFF_ROLES = 47;
	public static final int ATTEMPT_CREATE_STAFF_ROLE = 48;
	public static final int ATTEMPT_UPDATE_STAFF_ROLE = 49;
	public static final int ATTEMPT_DELETE_STAFF_ROLE = 50;
	public static final int CREATE_STAFF = 51;
	public static final int VIEW_STAFF = 52;
	public static final int UPDATE_STAFF = 53;
	public static final int DELETE_STAFF = 54;
	public static final int ATTEMPT_CREATE_STAFF = 55;
	public static final int ATTEMPT_CREATE_STAFF_PASSWORD = 56;
	public static final int ATTEMPT_VIEW_STAFF = 57;
	public static final int ATTEMPT_UPDATE_STAFF = 58;
	public static final int ATTEMPT_DELETE_STAFF = 59;
	public static final int CREATE_STAFF_ABSENCE = 60;
	public static final int VIEW_STAFF_ABSENCES = 61;
	public static final int VIEW_STAFF_ABSENCE = 62;
	public static final int UPDATE_STAFF_ABSENCE = 63;
	public static final int DELETE_STAFF_ABSENCE = 64;
	public static final int ATTEMPT_VIEW_STAFF_ABSENCES = 65;
	public static final int ATTEMPT_CREATE_STAFF_ABSENCE = 66;
	public static final int ATTEMPT_VIEW_STAFF_ABSENCE = 67;
	public static final int ATTEMPT_UPDATE_STAFF_ABSENCE = 68;
	public static final int ATTEMPT_DELETE_STAFF_ABSENCE = 69;
	public static final int VIEW_STAFF_AVAILABILITIES = 70;
	public static final int CREATE_STAFF_AVAILABILITY = 71;
	public static final int VIEW_STAFF_AVAILABILITY = 72;
	public static final int UPDATE_STAFF_AVAILABILITY = 73;
	public static final int DELETE_STAFF_AVAILABILITY = 74;
	public static final int ATTEMPT_VIEW_STAFF_AVAILABILITIES = 75;
	public static final int ATTEMPT_CREATE_STAFF_AVAILABILITY = 76;
	public static final int ATTEMPT_VIEW_STAFF_AVAILABILITY = 77;
	public static final int ATTEMPT_UPDATE_STAFF_AVAILABILITY = 78;
	public static final int ATTEMPT_DELETE_STAFF_AVAILABILITY = 79;
	public static final int VIEW_BOOKING_CALENDAR = 80;
	public static final int CREATE_BOOKING = 81;
	public static final int VIEW_BOOKING = 82;
	public static final int UPDATE_BOOKING = 83;
	public static final int ATTEMPT_VIEW_BOOKING_CALENDAR = 85;
	public static final int ATTEMPT_CREATE_BOOKING = 86;
	public static final int ATTEMPT_VIEW_BOOKING = 87;
	//TODO unused variable      v
	public static final int ATTEMPT_UPDATE_BOOKING = 88;
	public static final int LOG_IN = 90;
	public static final int ATTEMPT_LOG_IN_PASSWORD = 91;
	public static final int ATTEMPT_LOG_IN_STAFF_ID = 92;
	public static final int ATTEMPT_LOG_IN_STAFF_ID_DISABLED = 93;
	public static final int LOG_OUT = 94;
	public static final int UPDATE_STAFF_PASSWORD = 95;
	public static final int ATTEMPT_UPDATE_STAFF_PASSWORD = 96;
	public static final int VIEW_ACTION_LOG = 97;
	public static final int ATTEMPT_VIEW_ACTION_LOG = 98;
	public static final int VIEW_TRACER_ORDERS = 99;
	public static final int ATTEMPT_VIEW_TRACER_ORDERS = 100;
	public static final int IMPORT_PATIENTS = 101;
	public static final int ATTEMPT_IMPORT_PATIENTS = 102;
	public static final int EXPORT_PATIENTS = 103;
	public static final int ATTEMPT_EXPORT_PATIENTS = 104;
	public static final int CREATE_GENERIC_EVENT = 105;
	public static final int VIEW_GENERIC_EVENTS = 106;
	public static final int UPDATE_GENERIC_EVENT = 107;
	public static final int DELETE_GENERIC_EVENT = 108;
	public static final int ATTEMPT_VIEW_GENERIC_EVENTS = 109;
	public static final int ATTEMPT_CREATE_GENERIC_EVENT = 110;
	public static final int ATTEMPT_UPDATE_GENERIC_EVENT = 111;
	public static final int ATTEMPT_DELETE_GENERIC_EVENT = 112;

	/**
	 * A method for recording staff action. Date/Time of the action are generated in the database. Requires
	 * an integer for the type of action performed and an id of the object to which it is applied to.
	 * E.g. for updating a patient with id = 4356, actionPerformed would be 5 amd objectID - 4356.
	 *
	 * @param user            the Staff that performed the action
	 * @param actionPerformed the type of action performed (e.g. deleted patient)
	 * @param objectID        the objectID on which the action was performed
	 */
	public static void logAction(Staff user, int actionPerformed, int objectID) {

		ActionLog entity = new ActionLog(user, new DateTime(), actionPerformed, objectID);
		AbstractEntityUtils.createEntity(ActionLog.class, entity);
	}

	/**
	 * A method for recording staff action. Date/Time of the action are generated in the database. Requires
	 * an integer for the type of action performed and an id of the object to which it is applied to.
	 * E.g. for deleting a patient with id = 4356, actionPerformed would be 4 amd objectID - 4356.
	 *
	 * @param user            the Staff that performed the action
	 * @param actionPerformed the type of action performed (e.g. deleted patient)
	 * @param objectID        the objectID on which the action was performed
	 * @param note            the note about the action when it occurred
	 */
	public static void logAction(Staff user, int actionPerformed, int objectID, String note) {

		ActionLog entity = new ActionLog(user, new DateTime(), actionPerformed, objectID, note);
		AbstractEntityUtils.createEntity(ActionLog.class, entity);
	}

}
