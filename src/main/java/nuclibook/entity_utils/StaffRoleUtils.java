package nuclibook.entity_utils;

import nuclibook.models.StaffRole;

import java.util.List;

public class StaffRoleUtils extends AbstractEntityUtils {

	public static StaffRole getStaffRole(String id) {
		try {
			return getStaffRole(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static StaffRole getStaffRole(int id) {
		return getEntityById(StaffRole.class, id);
	}

	public static List<StaffRole> getAllStaffRoles() {
		return getAllStaffRoles(false);
	}

	public static List<StaffRole> getAllStaffRoles(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(StaffRole.class, "enabled", "1");
		} else {
			return getAllEntities(StaffRole.class);
		}
	}
}
