package nuclibook.entity_utils;

import nuclibook.models.*;
import org.joda.time.DateTime;

import java.util.List;

/**
 * A class for reading data from the staff table in the database.
 */
public class StaffUtils extends AbstractEntityUtils {

	/**
	 * Gets the {@link nuclibook.models.Staff} object with the specified ID.
	 *
	 * @param id the <code>Staff</code> ID
	 * @return the associated <code>Staff</code> object
	 */
	public static Staff getStaff(String id) {
		try {
			return getStaff(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Gets the {@link nuclibook.models.Staff} object with the specified ID.
	 *
	 * @param id the <code>Staff</code> ID
	 * @return the associated <code>Staff</code> object
	 */
	public static Staff getStaff(int id) {
		return getEntityById(Staff.class, id);
	}

	/**
	 * Gets the {@link nuclibook.models.Staff} object with the specified username.
	 *
	 * @param username the <code>Staff</code> username
	 * @return the associated <code>Staff</code> object with that username
	 */
	public static Staff getStaffByUsername(String username) {
		List<Staff> matched = getEntitiesByField(Staff.class, "username", username);
		if (matched == null || matched.size() != 1) {
			return null;
		}
		return matched.get(0);
	}

	/**
	 * Gets all the {@link nuclibook.models.Staff} objects in the database.
	 * <p>
	 * Can return data only for the <code>enabled</code> fields.
	 *
	 * @param enabledOnly specifies whether the method should get only <code>enabled Staff</code> records
	 * @return a list of <code>Staff</code> objects
	 */
	public static List<Staff> getAllStaff(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Staff.class, "enabled", "1");
		} else {
			return getAllEntities(Staff.class);
		}
	}

	/**
	 * Checks if the specified <code>username</code> already exists in the database.
	 *
	 * @param username the username to be checked
	 * @return true if the username already exists in the database; false otherwise
	 */
	public static boolean usernameExists(String username) {
		return (getStaffByUsername(username) != null);
	}

}
