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

    public static final int CREATE_TRACER = 6;
    public static final int VIEW_TRACER = 7;
    public static final int UPDATE_TRACER = 8;
    public static final int DELETE_TRACER = 9;

    public static final int CREATE_CAMERA_TYPE = 10;
    public static final int VIEW_CAMERA_TYPE = 11;
    public static final int UPDATE_CAMERA_TYPE = 12;
    public static final int DELETE_CAMERA_TYPE = 13;

    public static final int CREATE_CAMERA = 14;
    public static final int VIEW_CAMERA = 15;
    public static final int UPDATE_CAMERA = 16;
    public static final int DELETE_CAMERA = 17;

    public static final int CREATE_THERAPY = 18;
    public static final int VIEW_THERAPY = 19;
    public static final int UPDATE_THERAPY = 20;
    public static final int DELETE_THERAPY = 21;

    public static final int CREATE_STAFF_ROLE = 22;
    public static final int VIEW_STAFF_ROLE = 23;
    public static final int UPDATE_STAFF_ROLE = 24;
    public static final int DELETE_STAFF_ROLE = 25;

    public static final int CREATE_STAFF = 26;
    public static final int VIEW_STAFF = 27;
    public static final int UPDATE_STAFF = 28;
    public static final int DELETE_STAFF = 29;

    public static final int CREATE_STAFF_ABSENCE = 30;
    public static final int VIEW_STAFF_ABSENCE = 31;
    public static final int UPDATE_STAFF_ABSENCE = 32;
    public static final int DELETE_STAFF_ABSENCE = 33;

    public static final int CREATE_STAFF_AVAILABILITY = 34;
    public static final int VIEW_STAFF_AVAILABILITY = 35;
    public static final int UPDATE_STAFF_AVAILABILITY = 36;
    public static final int DELETE_STAFF_AVAILABILITY = 37;

    public static final int VIEW_BOOKING_CALENDAR = 38;
    public static final int CREATE_BOOKING = 39;
    public static final int VIEW_BOOKING = 40;
    public static final int UPDATE_BOOKING = 41;
    public static final int DELETE_BOOKING = 42;

    public static final int LOGGED_IN = 43;
    public static final int LOGGED_OUT = 44;
    public static final int UPDATE_STAFF_NAME = 45;
    public static final int UPDATE_STAFF_PASSWORD = 46;

    public static Map actionDescription = new HashMap<Integer, String>(){{
        put(VIEW_PATIENT_LIST, "Viewed patient log");
        put(CREATE_PATIENT, "Created a new patient");
        put(VIEW_PATIENT, "Viewed patient");
        put(UPDATE_PATIENT, "Updated patient");
        put(DELETE_PATIENT, "Deleted patient");
        put(CREATE_TRACER, "Created tracer");
        put(VIEW_TRACER, "Viewed tracer ");
        put(UPDATE_TRACER, "Updated tracer");
        put(DELETE_TRACER, "Deleted tracer");
        put(CREATE_CAMERA_TYPE, "Created camera type");
        put(VIEW_CAMERA_TYPE, "Viewed camera type");
        put(UPDATE_CAMERA_TYPE, "Updated camera type");
        put(DELETE_CAMERA_TYPE, "Deleted camera type");
        put(CREATE_CAMERA, "Created camera");
        put(VIEW_CAMERA, "Viewed camera");
        put(UPDATE_CAMERA, "Updated camera");
        put(DELETE_CAMERA, "Deleted camera");
        put(CREATE_THERAPY, "Created therapy");
        put(VIEW_THERAPY, "Viewed therapy");
        put(UPDATE_THERAPY, "Updated therapy");
        put(DELETE_THERAPY, "Deleted therapy");
        put(CREATE_STAFF_ROLE, "Created staff role");
        put(VIEW_STAFF_ROLE, "Viewed staff role");
        put(UPDATE_STAFF_ROLE, "Updated staff role");
        put(DELETE_STAFF_ROLE, "Deleted staff role");
        put(CREATE_STAFF, "Created staff member");
        put(VIEW_STAFF, "Viewed staff member");
        put(UPDATE_STAFF, "Updated staff member");
        put(DELETE_STAFF, "Deleted staff member");
        put(CREATE_STAFF_ABSENCE, "Created staff absence");
        put(VIEW_STAFF_ABSENCE, "Viewed staff absence");
        put(UPDATE_STAFF_ABSENCE, "Updated staff absence");
        put(DELETE_STAFF_ABSENCE, "Deleted staff absence");
        put(CREATE_STAFF_AVAILABILITY, "Created staff availability");
        put(VIEW_STAFF_AVAILABILITY, "Viewed staff availability");
        put(UPDATE_STAFF_AVAILABILITY, "Updated staff availability");
        put(DELETE_STAFF_AVAILABILITY, "Deleted staff availability");
        put(VIEW_BOOKING_CALENDAR, "Viewed booking calendar");
        put(CREATE_BOOKING, "Created booking");
        put(VIEW_BOOKING, "Viewed booking");
        put(UPDATE_BOOKING, "Updated booking");
        put(DELETE_BOOKING, "Deleted booking");
        put(LOGGED_IN, "Logged in");
        put(LOGGED_OUT, "Logged out");
        put(UPDATE_STAFF_NAME, "Changed staff name");
        put(UPDATE_STAFF_PASSWORD, "Changed staff password");
    }};

    /**
     * A method for recording staff action. Date/Time of the action are generated in the database. Requires
     * an integer for the type of action performed and an id of the object to which it is applied to.
     * E.g. for deleting a patient with id = 4356, actionPerformed would be 5 amd objectID - 4356.
     *
     * @param actionPerformed   the type of action performed (e.g. deleted patient)
     * @param objectID          the objectID on which the action was performed
     */
    public static void logAction(int actionPerformed, int objectID) {

        Staff loggedIn = SecurityUtils.getCurrentUser();
        if (loggedIn != null) {
            ActionLog entity = new ActionLog(loggedIn, new DateTime(), actionPerformed, objectID);
            AbstractEntityUtils.createEntity(ActionLog.class, entity);
        }
    }
}
