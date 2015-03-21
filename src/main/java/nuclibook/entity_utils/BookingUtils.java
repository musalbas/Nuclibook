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

/**
 * A class for reading data from the bookings table in the database.
 */
public class BookingUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.Booking} object with the specified ID.
     * @param id    the id of the object
     * @return  the associated <code>Booking</code> object
     */
	public static Booking getBooking(String id) {
		try {
			return getBooking(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

    /**
     * Gets the {@link nuclibook.models.Booking} object with the specified ID.
     * @param id    the id of the object
     * @return  the associated <code>Booking</code> object
     */
	public static Booking getBooking(int id) {
		return getEntityById(Booking.class, id);
	}

    /**
     * Gets the {@link nuclibook.models.Booking} objects with the specified {@link nuclibook.models.Staff} ID.
     * @param staffId    the <code>Staff</code> id
     * @return  a list of the associated <code>Booking</code> objects
     */
	public static List<Booking> getBookingsByStaffId(int staffId) {
		List<BookingStaff> bookingStaff = getEntitiesByField(BookingStaff.class, "staff_id", staffId);
		HashSet<Booking> bookings = new HashSet<>();
		for (BookingStaff bs : bookingStaff) {
			bookings.add(bs.getBooking());
		}
		return new ArrayList<>(bookings);
	}

    /**
     * Gets the {@link nuclibook.models.Booking} objects with the specified {@link nuclibook.models.Patient} ID.
     * @param patientId the <code>Patient</code> id
     * @return  a list of the associated <code>Booking</code> objects
     */
	public static List<Booking> getBookingsByPatientId(int patientId) {
		return getEntitiesByField(Booking.class, "patient_id", patientId);
	}

    /**
     * Gets the {@link nuclibook.models.Booking} objects with the specified value in the <code>status</code> field.
     * @param status    the status of the <code>Booking</code>
     * @return  a list of the associated <code>Booking</code> objects
     */
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

    /**
     * Gets the {@link nuclibook.models.Booking} objects within the specified date range.
     * @param startDate     the start date
     * @param endDate       the end date
     * @return  a list of the associated <code>Booking</code> objects
     */
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
