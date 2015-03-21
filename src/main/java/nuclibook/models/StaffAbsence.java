package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Model to represent an time instance in which the staff member would be absent.
 */
@DatabaseTable(tableName = "staff_absences")
public class StaffAbsence implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id", foreignAutoRefresh = true)
	private Staff staff;

	@DatabaseField
	private long from;

	@DatabaseField
	private long to;

    /**
     * Initialise a staff absence without fields.
     */
	public StaffAbsence() {
	}

    /**
     * Gets the ID of the staff absence.
     * @return the ID of the staff absence.
     */
	public Integer getId() {
		return id;
	}

    /**
     * Sets the ID of the staff absence.
     * @param id the ID of the staff absence.
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Gets the staff member who will be absent.
     * @return the staff member who will be absent.
     */
	public Staff getStaff() {
		return staff;
	}

    /**
     * Sets the staff member who will be absent.
     * @param staff the staff member who will be absent.
     */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

    /**
     * Gets the starting date and time of the absence.
     * @return the starting date and time of the absence.
     */
	public DateTime getFrom() {
		return new DateTime(from);
	}

    /**
     * Sets the starting date and time of the absence.
     * @param from the starting date and time of the absence.
     */
	public void setFrom(DateTime from) {
		this.from = from.getMillis();
	}

    /**
     * Gets the end date and time of the absence.
     * @return the end date and time of the absence.
     */
	public DateTime getTo() {
		return new DateTime(to);
	}

    /**
     * Sets the end date and time of the absence.
     * @param to the end date and time of the absence.
     */
	public void setTo(DateTime to) {
		this.to = to.getMillis();
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("id", getId().toString());

			put("from", getFrom().toString("YYYY-MM-dd HH:mm"));
			put("from-date", getFrom().toString("YYYY-MM-dd"));
			put("from-time", getFrom().toString("HH:mm"));

			put("to", getTo().toString("YYYY-MM-dd HH:mm"));
			put("to-date", getTo().toString("YYYY-MM-dd"));
			put("to-time", getTo().toString("HH:mm"));
		}};
	}
}
