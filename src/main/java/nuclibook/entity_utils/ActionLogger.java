package nuclibook.entity_utils;

import nuclibook.models.ActionLog;
import nuclibook.models.Staff;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * This class records user logging. Types of actions are stored as static final integers.
 * There is a static HashMap for associating the actionID (integer) with the action description.
 * Logging an action can be done by calling the static method logAction
 */
public class ActionLogger {

    public static final int VIEW_PATIENTS = 1;
    public static final int CREATE_PATIENT = 2;
    public static final int VIEW_PATIENT = 3;
    public static final int UPDATE_PATIENT = 4;
    public static final int DELETE_PATIENT = 5;
    public static final int ATTEMPT_VIEW_PATIENTS = 6;
    public static final int ATTEMPT_CREATE_PATIENT = 7;
    public static final int ATTEMPT_VIEW_PATIENT = 8;
    public static final int ATTEMPT_UPDATE_PATIENT = 9;
    public static final int ATTEMPT_DELETE_PATIENT = 10;
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
    public static final int DELETE_BOOKING = 84;
    public static final int ATTEMPT_VIEW_BOOKING_CALENDAR = 85;
    public static final int ATTEMPT_CREATE_BOOKING = 86;
    public static final int ATTEMPT_VIEW_BOOKING = 87;
    public static final int ATTEMPT_UPDATE_BOOKING = 88;
    public static final int ATTEMPT_DELETE_BOOKING = 89;
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

    public static Map actionDescription = new HashMap<Integer, String>() {{
        put(VIEW_PATIENTS, "Viewed all patients");
        put(CREATE_PATIENT, "Created a new patient");
        put(VIEW_PATIENT, "Viewed patient");
        put(UPDATE_PATIENT, "Updated patient");
        put(DELETE_PATIENT, "Deleted patient");
        put(ATTEMPT_VIEW_PATIENTS, "Attempted to view patients");
        put(ATTEMPT_CREATE_PATIENT, "Attempted to create a new patient");
        put(ATTEMPT_VIEW_PATIENT, "Attempted to view patient");
        put(ATTEMPT_UPDATE_PATIENT, "Attempted to update patient");
        put(ATTEMPT_DELETE_PATIENT, "Attempted to delete patient");

        put(CREATE_TRACER, "Created tracer");
        put(VIEW_TRACERS, "Viewed all tracers ");
        put(UPDATE_TRACER, "Updated tracer");
        put(DELETE_TRACER, "Deleted tracer");
        put(ATTEMPT_CREATE_TRACER, "Attempted to create tracer");
        put(ATTEMPT_VIEW_TRACERS, "Attempted to view all tracers ");
        put(ATTEMPT_UPDATE_TRACER, "Attempted to update tracer");
        put(ATTEMPT_DELETE_TRACER, "Attempted to delete tracer");

        put(CREATE_CAMERA_TYPE, "Created camera type");
        put(VIEW_CAMERA_TYPES, "Viewed all camera types");
        put(UPDATE_CAMERA_TYPE, "Updated camera type");
        put(DELETE_CAMERA_TYPE, "Deleted camera type");
        put(ATTEMPT_CREATE_CAMERA_TYPE, "Attempted to create camera type");
        put(ATTEMPT_VIEW_CAMERA_TYPES, "Attempted to view all camera types");
        put(ATTEMPT_UPDATE_CAMERA_TYPE, "Attempted to update camera type");
        put(ATTEMPT_DELETE_CAMERA_TYPE, "Attempted to delete camera type");

        put(CREATE_CAMERA, "Created camera");
        put(VIEW_CAMERAS, "Viewed all cameras");
        put(UPDATE_CAMERA, "Updated camera");
        put(DELETE_CAMERA, "Deleted camera");
        put(ATTEMPT_CREATE_CAMERA, "Attempted to create camera");
        put(ATTEMPT_VIEW_CAMERAS, "Attempted to view all cameras");
        put(ATTEMPT_UPDATE_CAMERA, "Attempted to update camera");
        put(ATTEMPT_DELETE_CAMERA, "Attempted to delete camera");

        put(CREATE_THERAPY, "Created therapy");
        put(VIEW_THERAPIES, "Viewed all therapies");
        put(UPDATE_THERAPY, "Updated therapy");
        put(DELETE_THERAPY, "Deleted therapy");
        put(ATTEMPT_CREATE_THERAPY, "Attempted to create therapy");
        put(ATTEMPT_VIEW_THERAPIES, "Attempted to view all therapies");
        put(ATTEMPT_UPDATE_THERAPY, "Attempted to update therapy");
        put(ATTEMPT_DELETE_THERAPY, "Attempted to delete therapy");

        put(CREATE_STAFF_ROLE, "Created staff role");
        put(VIEW_STAFF_ROLES, "Viewed all staff roles");
        put(UPDATE_STAFF_ROLE, "Updated staff role");
        put(DELETE_STAFF_ROLE, "Deleted staff role");
        put(ATTEMPT_CREATE_STAFF_ROLE, "Attempted to create staff role");
        put(ATTEMPT_VIEW_STAFF_ROLES, "Attempted to view all staff roles");
        put(ATTEMPT_UPDATE_STAFF_ROLE, "Attempted to update staff role");
        put(ATTEMPT_DELETE_STAFF_ROLE, "Attempted to delete staff role");

        put(CREATE_STAFF, "Created staff member");
        put(VIEW_STAFF, "Viewed all staff accounts");
        put(UPDATE_STAFF, "Updated staff member");
        put(DELETE_STAFF, "Deleted staff member");
        put(ATTEMPT_CREATE_STAFF, "Attempted to create staff account");
        put(ATTEMPT_CREATE_STAFF_PASSWORD, "Attempted to create staff member with invalid password");
        put(ATTEMPT_VIEW_STAFF, "Attempted to view all staff account");
        put(ATTEMPT_UPDATE_STAFF, "Attempted to update staff account");
        put(ATTEMPT_DELETE_STAFF, "Attempted to delete staff account");

        put(CREATE_STAFF_ABSENCE, "Created staff absence");
        put(VIEW_STAFF_ABSENCE, "Viewed staff absence");
        put(VIEW_STAFF_ABSENCES, "Viewed staff list en route to absences");
        put(UPDATE_STAFF_ABSENCE, "Updated staff absence");
        put(DELETE_STAFF_ABSENCE, "Deleted staff absence");
        put(ATTEMPT_CREATE_STAFF_ABSENCE, "Attempted to create staff absence");
        put(ATTEMPT_VIEW_STAFF_ABSENCE, "Attempted to view staff absence");
        put(ATTEMPT_VIEW_STAFF_ABSENCES, "Attempted to view staff list en route to absences");
        put(ATTEMPT_UPDATE_STAFF_ABSENCE, "Attempted to update staff absence");
        put(ATTEMPT_DELETE_STAFF_ABSENCE, "Attempted to delete staff absence");

        put(CREATE_STAFF_AVAILABILITY, "Created staff availability");
        put(VIEW_STAFF_AVAILABILITY, "Viewed staff availability");
        put(VIEW_STAFF_AVAILABILITIES, "Viewed staff list en route to availabilities");
        put(UPDATE_STAFF_AVAILABILITY, "Updated staff availability");
        put(DELETE_STAFF_AVAILABILITY, "Deleted staff availability");
        put(ATTEMPT_CREATE_STAFF_AVAILABILITY, "Attempted to create staff availability");
        put(ATTEMPT_VIEW_STAFF_AVAILABILITY, "Attempted to view staff availability");
        put(ATTEMPT_VIEW_STAFF_AVAILABILITIES, "Attempted to view staff list en route to availabilities");
        put(ATTEMPT_UPDATE_STAFF_AVAILABILITY, "Attempted to update staff availability");
        put(ATTEMPT_DELETE_STAFF_AVAILABILITY, "Attempted to delete staff availability");

        put(VIEW_BOOKING_CALENDAR, "Viewed booking calendar");
        put(ATTEMPT_VIEW_BOOKING_CALENDAR, "Attempted to view booking calendar");

        put(CREATE_BOOKING, "Created booking");
        put(VIEW_BOOKING, "Viewed booking");
        put(UPDATE_BOOKING, "Updated booking");
        put(DELETE_BOOKING, "Deleted booking");
        put(ATTEMPT_CREATE_BOOKING, "Attempted to create booking");
        put(ATTEMPT_VIEW_BOOKING, "Attempted to view booking");
        put(ATTEMPT_UPDATE_BOOKING, "Attempted to update booking");
        put(ATTEMPT_DELETE_BOOKING, "Attempted to delete booking");

        put(LOG_IN, "Logged in");
        put(ATTEMPT_LOG_IN_PASSWORD, "Attempted to log in with wrong password");
        put(ATTEMPT_LOG_IN_STAFF_ID, "Attempted to log in with staff ID that doesn't exist");
        put(ATTEMPT_LOG_IN_STAFF_ID_DISABLED, "Attempted to log in with a disabled staff ID");

        put(LOG_OUT, "Logged out");

        put(UPDATE_STAFF_PASSWORD, "Changed account password");
        put(ATTEMPT_UPDATE_STAFF_PASSWORD, "Attempted to change account password");

        put(VIEW_ACTION_LOG, "Viewed action log");
        put(ATTEMPT_VIEW_ACTION_LOG, "Attempted to view action log");

        put(ATTEMPT_VIEW_TRACER_ORDERS, "Attempted to view tracer orders");
        put(VIEW_TRACER_ORDERS, "Viewed tracer orders");

        put(IMPORT_PATIENTS, "Imported patients");
        put(ATTEMPT_IMPORT_PATIENTS, "Attempted to import patients");

        put(EXPORT_PATIENTS, "Exported patients");
        put(ATTEMPT_EXPORT_PATIENTS, "Attempted to export patients");

    }};

    /**
     * A method for recording staff action. Date/Time of the action are generated in the database. Requires
     * an integer for the type of action performed and an id of the object to which it is applied to.
     * E.g. for deleting a patient with id = 4356, actionPerformed would be 5 amd objectID - 4356.
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
     * E.g. for deleting a patient with id = 4356, actionPerformed would be 5 amd objectID - 4356.
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

    public static boolean isErrorAction(int numberOfAction) {
        int[] errorsList = {ATTEMPT_VIEW_PATIENTS, ATTEMPT_CREATE_PATIENT, ATTEMPT_VIEW_PATIENT, ATTEMPT_UPDATE_PATIENT, ATTEMPT_DELETE_PATIENT,
                ATTEMPT_VIEW_TRACERS, ATTEMPT_CREATE_TRACER, ATTEMPT_UPDATE_TRACER, ATTEMPT_DELETE_TRACER, ATTEMPT_VIEW_CAMERA_TYPES, ATTEMPT_CREATE_CAMERA_TYPE,
                ATTEMPT_UPDATE_CAMERA_TYPE, ATTEMPT_DELETE_CAMERA_TYPE, ATTEMPT_VIEW_CAMERAS, ATTEMPT_CREATE_CAMERA, ATTEMPT_UPDATE_CAMERA,
                ATTEMPT_DELETE_CAMERA, ATTEMPT_VIEW_THERAPIES, ATTEMPT_CREATE_THERAPY, ATTEMPT_UPDATE_THERAPY, ATTEMPT_DELETE_THERAPY,
                ATTEMPT_VIEW_STAFF_ROLES, ATTEMPT_CREATE_STAFF_ROLE, ATTEMPT_UPDATE_STAFF_ROLE, ATTEMPT_DELETE_STAFF_ROLE, ATTEMPT_CREATE_STAFF,
                ATTEMPT_CREATE_STAFF_PASSWORD, ATTEMPT_VIEW_STAFF, ATTEMPT_UPDATE_STAFF, ATTEMPT_DELETE_STAFF, ATTEMPT_VIEW_STAFF_ABSENCES,
                ATTEMPT_CREATE_STAFF_ABSENCE, ATTEMPT_VIEW_STAFF_ABSENCE, ATTEMPT_UPDATE_STAFF_ABSENCE, ATTEMPT_DELETE_STAFF_ABSENCE,
                ATTEMPT_VIEW_STAFF_AVAILABILITIES, ATTEMPT_CREATE_STAFF_AVAILABILITY, ATTEMPT_VIEW_STAFF_AVAILABILITY, ATTEMPT_UPDATE_STAFF_AVAILABILITY,
                ATTEMPT_DELETE_STAFF_AVAILABILITY, ATTEMPT_VIEW_BOOKING_CALENDAR, ATTEMPT_CREATE_BOOKING, ATTEMPT_VIEW_BOOKING, ATTEMPT_UPDATE_BOOKING,
                ATTEMPT_DELETE_BOOKING, ATTEMPT_LOG_IN_PASSWORD, ATTEMPT_LOG_IN_STAFF_ID, ATTEMPT_LOG_IN_STAFF_ID_DISABLED, ATTEMPT_UPDATE_STAFF_PASSWORD,
                ATTEMPT_VIEW_ACTION_LOG, ATTEMPT_VIEW_TRACER_ORDERS, ATTEMPT_IMPORT_PATIENTS, ATTEMPT_EXPORT_PATIENTS};

        boolean error = false;
        for (int anErrorsList : errorsList) {
            if (anErrorsList == numberOfAction) {
                error = true;
                break;
            }
        }
        return error;
    }

}
