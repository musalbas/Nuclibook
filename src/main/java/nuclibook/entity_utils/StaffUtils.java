package nuclibook.entity_utils;

import nuclibook.models.*;
import org.joda.time.DateTime;

import java.util.List;

public class StaffUtils extends AbstractEntityUtils {

	public static Staff getStaff(String id) {
		try {
			return getStaff(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Staff getStaff(int id) {
		return getEntityById(Staff.class, id);
	}

	public static Staff getStaffByUsername(String username) {
		List<Staff> matched = getEntitiesByField(Staff.class, "username", username);
		if (matched == null || matched.size() != 1) {
			return null;
		}
		return matched.get(0);
	}

	public static List<Staff> getAllStaff() {
		return getAllStaff(false);
	}

	public static List<Staff> getAllStaff(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Staff.class, "enabled", "1");
		} else {
			return getAllEntities(Staff.class);
		}
	}

	/**
	 * A method for checking if a staff member is available during a certain period, specified by the user.
	 *
	 * @param staff     the staff member whose availability we're checking
	 * @param startDate the start date of the queried time interval
	 * @param endDate   the end date of the queried time interval
	 * @return true if the staff member is available within the specified period; false otherwise
	 */
	public static boolean isAvailable(Staff staff, DateTime startDate, DateTime endDate) {
		int staffId = staff.getId();

		// endDate can't be earlier than startDate
		if (endDate.isBefore(startDate)) {
			return false;
		}

        /* STAFF DEFAULT AVAILABILITY */

		// convert dates to seconds past midnight
		long startDateSecondsPastMidnight = startDate.getSecondOfDay();
		long endDateSecondsPastMidnight = endDate.getSecondOfDay();
		int dayOfTheWeek = startDate.getDayOfWeek();

		// get availability for staff
		List<StaffAvailability> staffAvailabilities = StaffAvailabilityUtils.getAvailabilitiesByStaffId(staffId);
		boolean passedAvailableCheck = false;

		// check availability for staff
		for (StaffAvailability sa : staffAvailabilities) {
			if (sa.getDay() == dayOfTheWeek) {
				if (sa.getStartTime().getSecondsPastMidnight() <= startDateSecondsPastMidnight
						&& sa.getEndTime().getSecondsPastMidnight() >= endDateSecondsPastMidnight) {
					passedAvailableCheck = true;
					break;
				}
			}
		}
		if (!passedAvailableCheck) return false;

        /* STAFF ABSENCES */
		List<StaffAbsence> staffAbsences = StaffAbsenceUtils.getStaffAbsencesByStaffId(staffId);
		for (StaffAbsence sa : staffAbsences) {
			if ((sa.getFrom().isBefore(startDate) && sa.getTo().isAfter(endDate))
					|| (sa.getFrom().isAfter(startDate) && sa.getTo().isBefore(endDate))
					|| (sa.getFrom().isAfter(startDate) && sa.getFrom().isBefore(endDate))
					|| (sa.getTo().isAfter(startDate) && sa.getTo().isBefore(endDate))) {
				return false; // not allowed to overlap with ANY absences
			}
		}

        /* BOOKINGS */
        List<Booking> bookings = BookingUtils.getBookingsByStaffId(staffId);
        for(Booking b: bookings) {
            for(BookingSection bs: b.getBookingSections()) {
                if ((bs.getStart().isBefore(startDate) && bs.getEnd().isAfter(endDate))
                        || (bs.getStart().isAfter(startDate) && bs.getEnd().isBefore(endDate))
                        || (bs.getStart().isAfter(startDate) && bs.getStart().isBefore(endDate))
                        || (bs.getEnd().isAfter(startDate) && bs.getEnd().isBefore(endDate))) {
                    return false; // not allowed to overlap with ANY bookings
                }
            }
        }

		return true;
	}

	public static boolean usernameExists(String username) {
		return (getStaffByUsername(username) != null);
	}

}
