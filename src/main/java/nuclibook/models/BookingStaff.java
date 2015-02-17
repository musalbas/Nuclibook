package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "booking_staff")
public class BookingStaff {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "booking_id")
    private Booking booking;
    
    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staff;

    public BookingStaff(Booking booking, Staff staff) {
        this.booking = booking;
        this.staff = staff;
    }

    public BookingStaff() { //Empty constructor for ORMLite
    }

    public Integer getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
