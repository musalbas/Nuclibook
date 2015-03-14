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

    public static final int VIEW_PATIENT_LIST = 1;
    public static final int CREATE_PATIENT = 2;
    public static final int VIEW_PATIENT = 3;
    public static final int UPDATE_PATIENT = 4;
    public static final int DELETE_PATIENT = 5;
    public static final int ATTEMPT_VIEW_PATIENT_LIST = 6;
    public static final int ATTEMPT_CREATE_PATIENT = 7;
    public static final int ATTEMPT_VIEW_PATIENT = 8;
    public static final int ATTEMPT_UPDATE_PATIENT = 9;
    public static final int ATTEMPT_DELETE_PATIENT = 10;
    public static final int CREATE_TRACER = 11;
    public static final int VIEW_TRACER = 12;
    public static final int UPDATE_TRACER = 13;
    public static final int DELETE_TRACER = 14;
    public static final int ATTEMPT_CREATE_TRACER = 15;
    public static final int ATTEMPT_VIEW_TRACER = 16;
    public static final int ATTEMPT_UPDATE_TRACER = 17;
    public static final int ATTEMPT_DELETE_TRACER = 18;
    public static final int CREATE_CAMERA_TYPE = 19;
    public static final int VIEW_CAMERA_TYPE = 20;
    public static final int UPDATE_CAMERA_TYPE = 21;
    public static final int DELETE_CAMERA_TYPE = 22;
    public static final int ATTEMPT_CREATE_CAMERA_TYPE = 23;
    public static final int ATTEMPT_VIEW_CAMERA_TYPE = 24;
    public static final int ATTEMPT_UPDATE_CAMERA_TYPE = 25;
    public static final int ATTEMPT_DELETE_CAMERA_TYPE = 26;
    public static final int CREATE_CAMERA = 27;
    public static final int VIEW_CAMERA = 28;
    public static final int UPDATE_CAMERA = 29;
    public static final int DELETE_CAMERA = 30;
    public static final int ATTEMPT_CREATE_CAMERA = 31;
    public static final int ATTEMPT_VIEW_CAMERA = 32;
    public static final int ATTEMPT_UPDATE_CAMERA = 33;
    public static final int ATTEMPT_DELETE_CAMERA = 34;
    public static final int CREATE_THERAPY = 35;
    public static final int VIEW_THERAPY = 36;
    public static final int UPDATE_THERAPY = 37;
    public static final int DELETE_THERAPY = 38;
    public static final int ATTEMPT_CREATE_THERAPY = 39;
    public static final int ATTEMPT_VIEW_THERAPY = 40;
    public static final int ATTEMPT_UPDATE_THERAPY = 41;
    public static final int ATTEMPT_DELETE_THERAPY = 42;
    public static final int CREATE_STAFF_ROLE = 43;
    public static final int VIEW_STAFF_ROLE = 44;
    public static final int UPDATE_STAFF_ROLE = 45;
    public static final int DELETE_STAFF_ROLE = 46;
    public static final int ATTEMPT_CREATE_STAFF_ROLE = 47;
    public static final int ATTEMPT_VIEW_STAFF_ROLE = 48;
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
    public static final int VIEW_STAFF_ABSENCE = 61;
    public static final int UPDATE_STAFF_ABSENCE = 62;
    public static final int DELETE_STAFF_ABSENCE = 63;
    public static final int ATTEMPT_CREATE_STAFF_ABSENCE = 64;
    public static final int ATTEMPT_VIEW_STAFF_ABSENCE = 65;
    public static final int ATTEMPT_UPDATE_STAFF_ABSENCE = 66;
    public static final int ATTEMPT_DELETE_STAFF_ABSENCE = 67;
    public static final int CREATE_STAFF_AVAILABILITY = 68;
    public static final int VIEW_STAFF_AVAILABILITY = 69;
    public static final int UPDATE_STAFF_AVAILABILITY = 70;
    public static final int DELETE_STAFF_AVAILABILITY = 71;
    public static final int ATTEMPT_CREATE_STAFF_AVAILABILITY = 72;
    public static final int ATTEMPT_VIEW_STAFF_AVAILABILITY = 73;
    public static final int ATTEMPT_UPDATE_STAFF_AVAILABILITY = 74;
    public static final int ATTEMPT_DELETE_STAFF_AVAILABILITY = 75;
    public static final int VIEW_BOOKING_CALENDAR = 76;
    public static final int CREATE_BOOKING = 77;
    public static final int VIEW_BOOKING = 78;
    public static final int UPDATE_BOOKING = 79;
    public static final int DELETE_BOOKING = 80;
    public static final int ATTEMPT_VIEW_BOOKING_CALENDAR = 81;
    public static final int ATTEMPT_CREATE_BOOKING = 82;
    public static final int ATTEMPT_VIEW_BOOKING = 83;
    public static final int ATTEMPT_UPDATE_BOOKING = 84;
    public static final int ATTEMPT_DELETE_BOOKING = 85;
    public static final int LOG_IN = 86;
    public static final int ATTEMPT_LOG_IN_PASSWORD = 87;
    public static final int ATTEMPT_LOG_IN_STAFF_ID = 88;
    public static final int ATTEMPT_LOG_IN_STAFF_ID_DISABLED = 89;
    public static final int LOG_OUT = 90;
    public static final int ATTEMPT_LOG_OUT = 91;
    public static final int UPDATE_STAFF_PASSWORD = 92;
    public static final int ATTEMPT_UPDATE_STAFF_PASSWORD = 93;

    public static Map actionDescription = new HashMap<Integer, String>() {{
        put(VIEW_PATIENT_LIST, "Viewed patient log");
        put(CREATE_PATIENT, "Created a new patient");
        put(VIEW_PATIENT, "Viewed patient");
        put(UPDATE_PATIENT, "Updated patient");
        put(DELETE_PATIENT, "Deleted patient");
        put(ATTEMPT_VIEW_PATIENT_LIST, "Attempted to view patient log");
        put(ATTEMPT_CREATE_PATIENT, "Attempted to create a new patient");
        put(ATTEMPT_VIEW_PATIENT, "Attempted to view patient");
        put(ATTEMPT_UPDATE_PATIENT, "Attempted to update patient");
        put(ATTEMPT_DELETE_PATIENT, "Attempted to delete patient");

        put(CREATE_TRACER, "Created tracer");
        put(VIEW_TRACER, "Viewed tracer ");
        put(UPDATE_TRACER, "Updated tracer");
        put(DELETE_TRACER, "Deleted tracer");
        put(ATTEMPT_CREATE_TRACER, "Attempted to create tracer");
        put(ATTEMPT_VIEW_TRACER, "Attempted to view tracer ");
        put(ATTEMPT_UPDATE_TRACER, "Attempted to update tracer");
        put(ATTEMPT_DELETE_TRACER, "Attempted to delete tracer");

        put(CREATE_CAMERA_TYPE, "Created camera type");
        put(VIEW_CAMERA_TYPE, "Viewed camera type");
        put(UPDATE_CAMERA_TYPE, "Updated camera type");
        put(DELETE_CAMERA_TYPE, "Deleted camera type");
        put(ATTEMPT_CREATE_CAMERA_TYPE, "Attempted to create camera type");
        put(ATTEMPT_VIEW_CAMERA_TYPE, "Attempted to view camera type");
        put(ATTEMPT_UPDATE_CAMERA_TYPE, "Attempted to update camera type");
        put(ATTEMPT_DELETE_CAMERA_TYPE, "Attempted to delete camera type");

        put(CREATE_CAMERA, "Created camera");
        put(VIEW_CAMERA, "Viewed camera");
        put(UPDATE_CAMERA, "Updated camera");
        put(DELETE_CAMERA, "Deleted camera");
        put(ATTEMPT_CREATE_CAMERA, "Attempted to create camera");
        put(ATTEMPT_VIEW_CAMERA, "Attempted to view camera");
        put(ATTEMPT_UPDATE_CAMERA, "Attempted to update camera");
        put(ATTEMPT_DELETE_CAMERA, "Attempted to delete camera");

        put(CREATE_THERAPY, "Created therapy");
        put(VIEW_THERAPY, "Viewed therapy");
        put(UPDATE_THERAPY, "Updated therapy");
        put(DELETE_THERAPY, "Deleted therapy");
        put(ATTEMPT_CREATE_THERAPY, "Attempted to create therapy");
        put(ATTEMPT_VIEW_THERAPY, "Attempted to view therapy");
        put(ATTEMPT_UPDATE_THERAPY, "Attempted to update therapy");
        put(ATTEMPT_DELETE_THERAPY, "Attempted to delete therapy");

        put(CREATE_STAFF_ROLE, "Created staff role");
        put(VIEW_STAFF_ROLE, "Viewed staff role");
        put(UPDATE_STAFF_ROLE, "Updated staff role");
        put(DELETE_STAFF_ROLE, "Deleted staff role");
        put(ATTEMPT_CREATE_STAFF_ROLE, "Attempted to create staff role");
        put(ATTEMPT_VIEW_STAFF_ROLE, "Attempted to view staff role");
        put(ATTEMPT_UPDATE_STAFF_ROLE, "Attempted to update staff role");
        put(ATTEMPT_DELETE_STAFF_ROLE, "Attempted to delete staff role");

        put(CREATE_STAFF, "Created staff member");
        put(VIEW_STAFF, "Viewed staff member");
        put(UPDATE_STAFF, "Updated staff member");
        put(DELETE_STAFF, "Deleted staff member");
        put(ATTEMPT_CREATE_STAFF, "Attempted to create staff member");
        put(ATTEMPT_CREATE_STAFF_PASSWORD, "Attempted to create staff member with invalid password");
        put(ATTEMPT_VIEW_STAFF, "Attempted to view staff member");
        put(ATTEMPT_UPDATE_STAFF, "Attempted to update staff member");
        put(ATTEMPT_DELETE_STAFF, "Attempted to delete staff member");

        put(CREATE_STAFF_ABSENCE, "Created staff absence");
        put(VIEW_STAFF_ABSENCE, "Viewed staff absence");
        put(UPDATE_STAFF_ABSENCE, "Updated staff absence");
        put(DELETE_STAFF_ABSENCE, "Deleted staff absence");
        put(ATTEMPT_CREATE_STAFF_ABSENCE, "Attempted to create staff absence");
        put(ATTEMPT_VIEW_STAFF_ABSENCE, "Attempted to view staff absence");
        put(ATTEMPT_UPDATE_STAFF_ABSENCE, "Attempted to update staff absence");
        put(ATTEMPT_DELETE_STAFF_ABSENCE, "Attempted to delete staff absence");

        put(CREATE_STAFF_AVAILABILITY, "Created staff availability");
        put(VIEW_STAFF_AVAILABILITY, "Viewed staff availability");
        put(UPDATE_STAFF_AVAILABILITY, "Updated staff availability");
        put(DELETE_STAFF_AVAILABILITY, "Deleted staff availability");
        put(ATTEMPT_CREATE_STAFF_AVAILABILITY, "Attempted to create staff availability");
        put(ATTEMPT_VIEW_STAFF_AVAILABILITY, "Attempted to view staff availability");
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
        put(ATTEMPT_LOG_OUT, "Attempted to log out");

        put(UPDATE_STAFF_PASSWORD, "Changed staff password");
        put(ATTEMPT_UPDATE_STAFF_PASSWORD, "Attempted to change staff password");
    }};

    /**
     * A method for recording staff action. Date/Time of the action are generated in the database. Requires
     * an integer for the type of action performed and an id of the object to which it is applied to.
     * E.g. for deleting a patient with id = 4356, actionPerformed would be 5 amd objectID - 4356.
     *
     * @param actionPerformed the type of action performed (e.g. deleted patient)
     * @param objectID        the objectID on which the action was performed
     */
    public static void logAction(int actionPerformed, int objectID) {

        Staff loggedIn = SecurityUtils.getCurrentUser();
        ActionLog entity = new ActionLog(loggedIn, new DateTime(), actionPerformed, objectID);
        AbstractEntityUtils.createEntity(ActionLog.class, entity);
    }

    /**
     * A method for recording staff action. Date/Time of the action are generated in the database. Requires
     * an integer for the type of action performed and an id of the object to which it is applied to.
     * E.g. for deleting a patient with id = 4356, actionPerformed would be 5 amd objectID - 4356.
     *
     * @param actionPerformed the type of action performed (e.g. deleted patient)
     * @param objectID        the objectID on which the action was performed
     * @param note            the note about the action when it occurred
     */
    public static void logAction(int actionPerformed, int objectID, String note) {

        Staff loggedIn = SecurityUtils.getCurrentUser();
        ActionLog entity = new ActionLog(loggedIn, new DateTime(), actionPerformed, objectID, note);
        AbstractEntityUtils.createEntity(ActionLog.class, entity);
    }
}
