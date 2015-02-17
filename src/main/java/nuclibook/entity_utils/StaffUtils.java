package nuclibook.entity_utils;

import nuclibook.models.Staff;
import java.util.List;

public class StaffUtils extends AbstractEntityUtil {
    public static Staff getStaff(int id) {
        return getEntityById(id, Staff.class);
    }

    public static List<Staff> getAllStaff() {
        return geAllEntites(Staff.class);
    }
}
