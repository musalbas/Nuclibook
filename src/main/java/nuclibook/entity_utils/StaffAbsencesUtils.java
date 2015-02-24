package nuclibook.entity_utils;

import nuclibook.models.StaffAbsence;

import java.util.List;

public class StaffAbsencesUtils extends AbstractEntityUtils {

    public static StaffAbsence getStaffAbsenceById(String id) {
        try {
            return getStaffAbsenceById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<StaffAbsence> getStaffAbsencesByStaffId(String staffId) {
        try {
            return getStaffAbsencesByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static StaffAbsence getStaffAbsenceById(int id) {
        return getEntityById(StaffAbsence.class, id);
    }

    public static List<StaffAbsence> getStaffAbsencesByStaffId(int staffId) {
        return getEntitiesByField(StaffAbsence.class, "staff_id", staffId);
    }

    public static List<StaffAbsence> getAllStaffAbsences() {
        return getAllEntities(StaffAbsence.class);
    }
}
