package nuclibook.entity_utils;

import nuclibook.models.Permission;

import java.util.List;

public class PermissionUtils extends AbstractEntityUtils {

	public static Permission getPermission(String id) {
		try {
			return getPermission(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Permission getPermission(int id) {
		return getEntityById(Permission.class, id);
	}

	public static List<Permission> getAllPermissions() {
		return getAllEntities(Permission.class);
	}
}
