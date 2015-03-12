package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import nuclibook.models.Booking;
import nuclibook.models.BookingSection;
import nuclibook.models.Staff;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BookingUtils extends AbstractEntityUtils {

	public static Staff getBooking(String id) {
		try {
			return getBooking(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Staff getBooking(int id) {
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
        return getEntitiesByField(Booking.class, "staff_id", staffId);
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
		HashSet<Booking> bookings = new HashSet<>();
		for (BookingSection bs : bookingSections) {
			bookings.add(bs.getBooking());
		}

		return new ArrayList<>(bookings);
	}

}
