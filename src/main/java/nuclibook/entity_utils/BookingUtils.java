package nuclibook.entity_utils;

import nuclibook.models.Booking;
import nuclibook.models.Staff;

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

}
