package nuclibook.entity_utils;

import nuclibook.models.Medicine;
import nuclibook.models.StaffAvailabilities;

import java.util.List;

public class StaffAvailabilityUtils extends AbstractEntityUtils {

    public static StaffAvailabilities getAvailabilityById(String id) {
        try {
            return getAvailabilityById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<StaffAvailabilities> getAvailabilitiesByStaffId(String id) {
		try {
			return getAvailabilitiesByStaffId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static StaffAvailabilities getAvailabilityById(int id) {
		return getEntityById(StaffAvailabilities.class, id);
	}

    public static List<StaffAvailabilities> getAvailabilitiesByStaffId(int id) {
        return getEntitiesByField(StaffAvailabilities.class, "staff_id", id);
    }

	public static List<Medicine> getAllAvailabilities() {
			return getAllEntities(StaffAvailabilities.class);
	}
}
