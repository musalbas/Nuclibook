package nuclibook.entity_utils;

import nuclibook.models.Staff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class records user logging.
 * Logging an action can be done by calling the static method logAction
 */
public class UserLog {

    public static final int VIEW_PATIENT_LIST = 1;
    public static final int CREATE_PATIENT = 2;
    public static final int VIEW_PATIENT = 3;
    public static final int UPDATE_PATIENT = 4;
    public static final int DELETE_PATIENT = 5;

    public static final int CREATE_MEDICINE = 6;
    public static final int VIEW_MEDICINE = 7;
    public static final int UPDATE_MEDICINE = 8;
    public static final int DELETE_MEDICINE = 9;

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

    public static final int VIEW_BOOKING_CALENDAR = 34;
    public static final int CREATE_BOOKING = 35;
    public static final int VIEW_BOOKING = 36;
    public static final int UPDATE_BOOKING = 37;
    public static final int DELETE_BOOKING = 38;

    public static final int LOGGED_IN = 39;
    public static final int LOGGED_OUT = 40;
    public static final int UPDATE_STAFF_NAME = 41;
    public static final int UPDATE_STAFF_PASSWORD = 42;

    public static Map actionDescription = new HashMap<Integer, String>(){{
        put(VIEW_PATIENT_LIST, "Viewed patient log");
        put(CREATE_PATIENT, "");
        put(VIEW_PATIENT, "");
        put(UPDATE_PATIENT, "");
        put(DELETE_PATIENT, "");
        put(CREATE_MEDICINE, "");
        put(VIEW_MEDICINE, "");
        put(UPDATE_MEDICINE, "");
        put(DELETE_MEDICINE, "");
        put(CREATE_CAMERA_TYPE, "");
        put(VIEW_CAMERA_TYPE, "");
        put(UPDATE_CAMERA_TYPE, "");
        put(DELETE_CAMERA_TYPE, "");
        put(CREATE_CAMERA, "");
        put(VIEW_CAMERA, "");
        put(UPDATE_CAMERA, "");
        put(DELETE_CAMERA, "");
        put(CREATE_THERAPY, "");
        put(VIEW_THERAPY, "");
        put(UPDATE_THERAPY, "");
        put(DELETE_THERAPY, "");
        put(CREATE_STAFF_ROLE, "");
        put(VIEW_STAFF_ROLE, "");
        put(UPDATE_STAFF_ROLE, "");
        put(DELETE_STAFF_ROLE, "");
        put(CREATE_STAFF, "");
        put(VIEW_STAFF, "");
        put(UPDATE_STAFF, "");
        put(DELETE_STAFF, "");
        put(CREATE_STAFF_ABSENCE, "");
        put(VIEW_STAFF_ABSENCE, "");
        put(UPDATE_STAFF_ABSENCE, "");
        put(DELETE_STAFF_ABSENCE, "");
        put(VIEW_BOOKING_CALENDAR, "");
        put(CREATE_BOOKING, "");
        put(VIEW_BOOKING, "");
        put(UPDATE_BOOKING, "");
        put(DELETE_BOOKING, "");
        put(LOGGED_IN, "");
        put(LOGGED_OUT, "");
        put(UPDATE_STAFF_NAME, "");
        put(UPDATE_STAFF_PASSWORD, "");

    }};

    //object id, user, log action id, date, time
    public static boolean logAction(int actionPerformed) {

        Staff loggedIn = SecurityUtils.getCurrentUser();
        if (loggedIn == null) {
            return false;

        } else {

            return true;
        }
    }
}
