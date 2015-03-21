package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model to represent a relationship between bookings and staff that created them.
 */
@DatabaseTable(tableName = "booking_staff")
public class BookingStaff {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "booking_id", foreignAutoRefresh = true)
	private Booking booking;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id", foreignAutoRefresh = true)
	private Staff staff;

    /**
     * Initialise a booking and staff relationship.
     * @param booking The Booking object that was created.
     * @param staff The Staff object that corresponds to the person who created the booking.
     */
	public BookingStaff(Booking booking, Staff staff) {
		this.booking = booking;
		this.staff = staff;
	}

    /**
     * Initialise the booking and staff relationship with no fields.
     */
	public BookingStaff() {
	}

    /**
     * Gets the ID of the BookingStaff tuple in the database.
     * @return the ID of the BookingStaff tuple in the database.
     */
	public Integer getId() {
		return id;
	}

    /**
     * Sets the ID of the BookingStaff tuple in the database.
     * @param id the ID of the BookingStaff tuple in the database.
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Gets the Booking object.
     * @return the Booking object.
     */
	public Booking getBooking() {
		return booking;
	}

    /**
     * Sets the Booking object.
     * @param booking the Booking object
     */
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

    /**
     * Gets the Staff object.
     * @return the Staff object.
     */
	public Staff getStaff() {
		return staff;
	}

    /**
     * Sets the Staff object.
     * @param staff the Staff object.
     */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}
