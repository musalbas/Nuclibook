package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import nuclibook.models.Booking;
import nuclibook.models.BookingSection;
import nuclibook.models.BookingStaff;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.*;

public class BookingUtils extends AbstractEntityUtils {

    public static Booking getBooking(String id) {
        try {
            return getBooking(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Booking getBooking(int id) {
        return getEntityById(Booking.class, id);
    }

    public static List<Booking> getBookingsByStaffId(String staffId) {
        try {
            return getBookingsByStaffId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<Booking> getBookingsByStaffId(int staffId) {
        List<BookingStaff> bookingStaff = getEntitiesByField(BookingStaff.class, "staff_id", staffId);
        HashSet<Booking> bookings = new HashSet<>();
        for (BookingStaff bs : bookingStaff) {
            bookings.add(bs.getBooking());
        }
        return new ArrayList<>(bookings);
    }

    public static List<Booking> getBookingsByPatientId(String patientId) {
        try {
            return getBookingsByPatientId(Integer.parseInt(patientId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<Booking> getBookingsByPatientId(int patientId) {
        return getEntitiesByField(Booking.class, "patient_id", patientId);
    }

    public static List<Booking> getBookingsByStatus(String status) {
        List<Booking> bookings = getEntitiesByField(Booking.class, "status", status);
        if (bookings == null) return null;

        // sort bookings
        bookings.sort(new Comparator<Booking>() {
            @Override
            public int compare(Booking o1, Booking o2) {
                BookingSection bs1 = o1.getBookingSections().isEmpty() ? null : o1.getBookingSections().get(0);
                BookingSection bs2 = o2.getBookingSections().isEmpty() ? null : o2.getBookingSections().get(0);
                if (bs1 == null) return 1;
                if (bs2 == null) return -1;
                return bs1.getStart().compareTo(bs2.getStart());
            }
        });

        return bookings;
    }

    public static List<Booking> getBookingsByDateRange(DateTime startDate, DateTime endDate) {
        // find all booking sections between the supplied dates
        Dao<BookingSection, Integer> dao = acquireDao(BookingSection.class);
        QueryBuilder<BookingSection, Integer> query = dao.queryBuilder();
        List<BookingSection> bookingSections;
        try {
            // start and end must be inside the period
            query.where().between("start", startDate.getMillis(), endDate.getMillis());
            query.where().between("end", startDate.getMillis(), endDate.getMillis());
            PreparedQuery<BookingSection> preparedQuery = query.prepare();
            bookingSections = dao.query(preparedQuery);
        } catch (SQLException | NullPointerException e) {
            return new ArrayList<>();
        }

        // get all unique bookings from the sections
        HashMap<Integer, Booking> bookings = new HashMap<>();
        for (BookingSection bs : bookingSections) {
            bookings.put(bs.getBooking().getId(), bs.getBooking());
        }

        return new ArrayList<>(bookings.values());
    }

}
