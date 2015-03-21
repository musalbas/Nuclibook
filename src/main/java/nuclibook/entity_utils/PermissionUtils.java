package nuclibook.entity_utils;

import nuclibook.models.Permission;

import java.util.List;

/**
 * A class for reading data from the permissions table in the database.
 */
public class PermissionUtils extends AbstractEntityUtils {

	/**
	 * Gets the {@link nuclibook.models.Permission} object with the specified ID.
	 *
	 * @param id the <code>Permission</code> ID
	 * @return the associated <code>Permission</code> object
	 */
	public static Permission getPermission(String id) {
		try {
			return getPermission(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Gets the {@link nuclibook.models.Permission} object with the specified ID.
	 *
	 * @param id the <code>Permission</code> ID
	 * @return the associated <code>Permission</code> object
	 */
	public static Permission getPermission(int id) {
		return getEntityById(Permission.class, id);
	}

	/**
	 * Gets all the {@link nuclibook.models.Permission} objects in the database.
	 *
	 * @return a list of all the <code>Permission</code> objects
	 */
	public static List<Permission> getAllPermissions() {
		return getAllEntities(Permission.class);
	}
}
