package nuclibook.entity_utils;

import nuclibook.models.StaffAbsence;

import java.util.List;

public class StaffAbsenceUtils extends AbstractEntityUtils {

    public static StaffAbsence getStaffAbsence(String id) {
        try {
            return getStaffAbsence(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static StaffAbsence getStaffAbsence(int id) {
        return getEntityById(StaffAbsence.class, id);
    }

    public static List<StaffAbsence> getStaffAbsencesByStaffId(String staffId) {
        try {
            return getStaffAbsencesByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<StaffAbsence> getStaffAbsencesByStaffId(int staffId) {
        return getEntitiesByField(StaffAbsence.class, "staff_id", staffId);
    }

    public static List<StaffAbsence> getAllStaffAbsences() {
        return getAllEntities(StaffAbsence.class);
    }
}
