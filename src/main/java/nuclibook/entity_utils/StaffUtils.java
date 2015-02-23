package nuclibook.entity_utils;

import nuclibook.models.*;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
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
    public boolean isAvailable(Staff staff, Date startDate, Date endDate) {
        int staffId = staff.getId();

        // endDate can't be earlier than startDate
        if (endDate.getTime() < startDate.getTime()) {
            return false;
        }

        /*STAFF DEFAULT AVAILABILITY */
        //convert dates to seconds past midnight
        long startDateSecondsPastMidnight = startDate.getTime() % (60*60*24);
        long endDateSecondsPastMidnight = endDate.getTime() % (60*60*24);
        int dayOfTheWeek = (DateUtils.toCalendar(startDate).get(Calendar.DAY_OF_WEEK) + 6) % 7;
        if(dayOfTheWeek == 0) dayOfTheWeek = 7;

        boolean passedAvailableCheck = false;

        //get availability for staff id
        List<StaffAvailability> staffAvailabilities = StaffAvailabilityUtils.getAvailabilitiesByStaffId(staffId);

        for (StaffAvailability sa : staffAvailabilities) {
            if (sa.getDay() == dayOfTheWeek) { //check if same day of the week
                if (sa.getStartTime().getSecondsPastMidnight() <= startDateSecondsPastMidnight
                        && sa.getEndTime().getSecondsPastMidnight() >= endDateSecondsPastMidnight) {
                    passedAvailableCheck = true;
                    break;
                }
            }
        }

        if (!passedAvailableCheck) return false;

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

        return true;
    }
}
