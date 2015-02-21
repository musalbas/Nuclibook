package nuclibook.entity_utils;

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

    public static List<StaffAvailabilities> getAvailabilitiesByStaffId(String staffId) {
        try {
            return getAvailabilitiesByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static StaffAvailabilities getAvailabilityById(int id) {
        return getEntityById(StaffAvailabilities.class, id);
    }

    public static List<StaffAvailabilities> getAvailabilitiesByStaffId(int staffId) {
        return getEntitiesByField(StaffAvailabilities.class, "staff_id", staffId);
    }

    public static List<StaffAvailabilities> getAllAvailabilities() {
        return getAllEntities(StaffAvailabilities.class);
    }
}
