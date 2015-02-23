package nuclibook.entity_utils;

import nuclibook.models.StaffAbsences;

import java.util.List;

public class StaffAbsencesUtils extends AbstractEntityUtils {

    public static StaffAbsences getStaffAbsenceById(String id) {
        try {
            return getStaffAbsenceById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<StaffAbsences> getStaffAbsencesByStaffId(String staffId) {
        try {
            return getStaffAbsencesByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static StaffAbsences getStaffAbsenceById(int id) {
        return getEntityById(StaffAbsences.class, id);
    }

    public static List<StaffAbsences> getStaffAbsencesByStaffId(int staffId) {
        return getEntitiesByField(StaffAbsences.class, "staff_id", staffId);
    }

    public static List<StaffAbsences> getAllStaffAbsences() {
        return getAllEntities(StaffAbsences.class);
    }
}
