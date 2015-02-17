package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "booking_staff")
public class BookingStaff {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "booking_id")
    private Booking bookingID;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;

    public BookingStaff(Booking bookingID, Staff staffID) {
        this.bookingID = bookingID;
        this.staffID = staffID;
    }

    public BookingStaff() { //Empty constructor for ORMLite
    }

    public Integer getId() {
        return id;
    }

    public Booking getBookingID() {
        return bookingID;
    }

    public void setBookingID(Booking bookingID) {
        this.bookingID = bookingID;
    }

    public Staff getStaffID() {
        return staffID;
    }

    public void setStaffID(Staff staffID) {
        this.staffID = staffID;
    }
}
