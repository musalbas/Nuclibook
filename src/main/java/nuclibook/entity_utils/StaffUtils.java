package nuclibook.entity_utils;

import nuclibook.models.*;

import java.util.Date;
import java.util.List;

public class StaffUtils extends AbstractEntityUtils {

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
     * @param staffId       the staff member ID whose availability we're checking
     * @param startDate     the start date of the queried time interval
     * @param endDate       the end date of the queried time interval
     *
     * @return              true if the staff member is available within the specified period; false otherwise
     */
    public boolean isAvailable(int staffId, Date startDate, Date endDate) {

        //endDate can't be earlier than startDate
        if (endDate.getTime() < startDate.getTime()) {
            return false;
        }

        //check if staff id exists
        if (getEntityById(Staff.class, staffId) == null) {
            return false;
        }

        /*BOOKINGS*/
        List<BookingStaff> bookingStaff = BookingStaffUtils.getBookingStaffByStaffId(staffId);
        Booking booking;
        for(BookingStaff bs : bookingStaff) {
            booking = bs.getBooking();

            if( (booking.getStart().compareTo(startDate) <= 0 && booking.getEnd().compareTo(endDate) >= 0)
                    || (booking.getStart().compareTo(startDate) >= 0 && booking.getStart().compareTo(endDate) <= 0)
                    || (booking.getEnd().compareTo(startDate) >= 0 && booking.getEnd().compareTo(endDate) <= 0)
                    || (booking.getStart().compareTo(startDate) >= 0 && booking.getEnd().compareTo(endDate) <= 0)) {
                return false; //not allowed to overlap with ANY bookings
            }
        }

        /* STAFF ABSENCES */
        List<StaffAbsences> staffAbsences = StaffAbsencesUtils.getStaffAbsencesByStaffId(staffId);

        for(StaffAbsences sa : staffAbsences) {
            if( (sa.getFrom().compareTo(startDate) <= 0 && sa.getTo().compareTo(endDate) >= 0)
                    || (sa.getFrom().compareTo(startDate) >= 0 && sa.getFrom().compareTo(endDate) <= 0)
                    || (sa.getTo().compareTo(startDate) >= 0 && sa.getTo().compareTo(endDate) <= 0)
                    || (sa.getFrom().compareTo(startDate) >= 0 && sa.getTo().compareTo(endDate) <= 0)) {
                return false; //not allowed to overlap with ANY absences
            }
        }

        /*STAFF DEFAULT AVAILABILITY */
        //convert dates to seconds past midnight
        int startDateSecondsPastMidnight = (startDate.getHours() * 3600) + (startDate.getMinutes() * 60) + startDate.getSeconds();
        int endDateSecondsPastMidnight = (endDate.getHours() * 3600) + (endDate.getMinutes() * 60) + endDate.getSeconds();

        int dayOfTheWeek = startDate.getDay() + 1;

        //get availability for staff id
        List<StaffAvailabilities> staffAvailabilities = StaffAvailabilityUtils.getAvailabilitiesByStaffId(staffId);
        Availability availability;

        for (StaffAvailabilities sa : staffAvailabilities) {
            availability = sa.getAvailability();

            if (availability.getDay().getValue() == dayOfTheWeek) { //check if same day of the week
                if (availability.getStartTime().getSecondsPastMidnight() <= startDateSecondsPastMidnight
                        && availability.getEndTime().getSecondsPastMidnight() >= endDateSecondsPastMidnight) {
                    return true; //we can return true at this point as all the other checks have not failed
                }
            }
        }

        return false;
    }
}
