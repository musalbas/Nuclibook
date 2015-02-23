package nuclibook.entity_utils;

import nuclibook.models.BookingStaff;

import java.util.List;

public class BookingStaffUtils extends AbstractEntityUtils {

    public static BookingStaff getBookingStaffById(String id) {
        try {
            return getBookingStaffById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<BookingStaff> getBookingStaffByStaffId(String staffId) {
        try {
            return getBookingStaffByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BookingStaff getBookingStaffById(int id) {
        return getEntityById(BookingStaff.class, id);
    }

    public static List<BookingStaff> getBookingStaffByStaffId(int staffId) {
        return getEntitiesByField(BookingStaff.class, "staff_id", staffId);
    }

    public static List<BookingStaff> getAllBookingStaff() {
        return getAllEntities(BookingStaff.class);
    }
}
