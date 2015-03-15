package nuclibook.constants;

/**
 * The values in this enum correspond to the various permissions that exist in the system.
 *
 * Their names must correspond exactly with the `permissions.label` field in the database.
 */

public enum P {

	EDIT_APPOINTMENTS,
	EDIT_CAMERAS,
	EDIT_TRACERS,
	EDIT_PATIENTS,
	EDIT_STAFF,
	EDIT_STAFF_ABSENCES,
	EDIT_STAFF_AVAILABILITIES,
	EDIT_STAFF_ROLES,
	EDIT_THERAPIES,

	VIEW_ACTION_LOG,
	VIEW_APPOINTMENTS,
    VIEW_APPOINTMENT_DETAILS,
	VIEW_CAMERAS,
	VIEW_TRACERS,
	VIEW_PATIENT_DETAILS,
	VIEW_PATIENT_LIST,
	VIEW_STAFF,
	VIEW_STAFF_ABSENCES,
	VIEW_STAFF_AVAILABILITIES,
	VIEW_STAFF_ROLES,
	VIEW_THERAPIES,

}
