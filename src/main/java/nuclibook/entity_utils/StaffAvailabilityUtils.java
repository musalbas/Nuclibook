package nuclibook.entity_utils;

import nuclibook.models.StaffAvailability;

import java.util.List;

public class StaffAvailabilityUtils extends AbstractEntityUtils {

    public static StaffAvailability getAvailabilityById(String id) {
        try {
            return getAvailabilityById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<StaffAvailability> getAvailabilityByStaffId(String staffId) {
        try {
            return getAvailabilityByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static StaffAvailability getAvailabilityById(int id) {
        return getEntityById(StaffAvailability.class, id);
    }

    public static List<StaffAvailability> getAvailabilityByStaffId(int staffId) {
        return getEntitiesByField(StaffAvailability.class, "staff_id", staffId);
    }

    public static List<StaffAvailability> getAllStaffAvailability() {
        return getAllEntities(StaffAvailability.class);
    }
}
