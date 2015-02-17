package nuclibook.entity_utils;

import nuclibook.models.Staff;

import java.util.List;

public class StaffUtils extends AbstractEntityUtil {

	public static Staff getStaff(int id) {
		return getEntityById(Staff.class, id);
	}

	public static Staff getStaffByUsername(String username) {
		List<Staff> matched = getEntityByField(Staff.class, "username", username);
		if (matched == null || matched.size() != 1) {
			return null;
		}
		return matched.get(0);
	}

	public static List<Staff> getAllStaff() {
		return getAllEntites(Staff.class);
	}
}
