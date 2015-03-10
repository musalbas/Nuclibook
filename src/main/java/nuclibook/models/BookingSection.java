package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "booking_sections")
public class BookingSection {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "booking_id", foreignAutoRefresh = true)
	private Booking booking;

	@DatabaseField
	private long start;

	@DatabaseField
	private long end;

	public BookingSection() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	/**
	 * Get the booking section start time.
	 *
	 * @return The booking start time.
	 */
	public DateTime getStart() {
		return new DateTime(start);
	}

	/**
	 * Set the booking section start time.
	 *
	 * @param start The booking start time.
	 */
	public void setStart(DateTime start) {
		this.start = start.getMillis();
	}

	/**
	 * Get the booking section end time.
	 *
	 * @return The booking end time.
	 */
	public DateTime getEnd() {
		return new DateTime(end);
	}

	/**
	 * Set the booking section end time.
	 *
	 * @param end The booking end time.
	 */
	public void setEnd(DateTime end) {
		this.end = end.getMillis();
	}

}
