package nuclibook.entity_utils;

import nuclibook.models.Staff;

import java.util.List;

public class StaffUtils extends AbstractEntityUtils {

	public static Staff getStaff(int id) {
		return getEntityById(Staff.class, id);
	}

	public static Staff getStaffByUsername(String username) {
		List<Staff> matched = getEntitiesByField(Staff.class, "username", username);
		if (matched == null || matched.size() != 1) {
			return null;
		}
		return matched.get(0);
	}

	public static List<Staff> getAllStaff() {
		return getAllStaff(false);
	}

	public static List<Staff> getAllStaff(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Staff.class, "enabled", "1");
		} else {
			return getAllEntities(Staff.class);
		}
	}
}
