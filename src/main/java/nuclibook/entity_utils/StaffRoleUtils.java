package nuclibook.entity_utils;

import nuclibook.models.StaffRole;

import java.util.List;

/**
 * A class for reading data from the staff_roles table in the database.
 */
public class StaffRoleUtils extends AbstractEntityUtils {

	/**
	 * Gets the {@link nuclibook.models.StaffRole} object with the specified ID.
	 *
	 * @param id the <code>StaffRole</code> ID
	 * @return the associated <code>StaffRole</code> object
	 */
	public static StaffRole getStaffRole(String id) {
		try {
			return getStaffRole(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Gets the {@link nuclibook.models.StaffRole} object with the specified ID.
	 *
	 * @param id the <code>StaffRole</code> ID
	 * @return the associated <code>StaffRole</code> object
	 */
	public static StaffRole getStaffRole(int id) {
		return getEntityById(StaffRole.class, id);
	}

	/**
	 * Gets all the {@link nuclibook.models.StaffRole} objects in the database.
	 *
	 * @return a list of all <code>StaffRole</code> objects
	 */
	public static List<StaffRole> getAllStaffRoles(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(StaffRole.class, "enabled", "1");
		} else {
			return getAllEntities(StaffRole.class);
		}
	}
}
