package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

/**
 * Model to represent an entry in the action log.
 */
@DatabaseTable(tableName = "action_log")
public class ActionLog {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private long when;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
	private Staff staff;

	@DatabaseField
	private Integer action;

	@DatabaseField(canBeNull = true, columnName = "associated_id")
	private Integer associatedId;

	@DatabaseField
	private String note;

	/**
	 * Initialise action log with no fields.
	 */
	public ActionLog() {
	}

	/**
	 * Initialise action log.
	 * @param staff Staff who performed the action.
	 * @param when Date the action was performed.
	 * @param action The action (constant from entity_utils.ActionLogger).
	 * @param associatedId The ID of the object that the action was performed on.
	 */
	public ActionLog(Staff staff, DateTime when, Integer action, Integer associatedId) {
		this.staff = staff;
		this.action = action;
		this.associatedId = associatedId;
		setWhen(when);
	}

	/**
	 * Get the ID of the action.
	 * @return The ID of the action.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Set the ID of the action.
	 * @param id The ID of the action.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Get the date the action was performed.
	 * @return The date the action was performed.
	 */
	public DateTime getWhen() {
		return new DateTime(when);
	}

	/**
	 * Set the date the action was performed.
	 * @param when The date the action was performed.
	 */
	public void setWhen(DateTime when) {
		this.when = when.getMillis();
	}

	/**
	 * Get the member of staff who performed the action.
	 * @return The member of staff who performed the action.
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * Set the member of staff who performed the action.
	 * @return The member of staff who performed the action.
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	/**
	 * Get the action that was performed.
	 * @return The action that was performed.
	 */
	public Integer getAction() {
		return action;
	}


	/**
	 * Set the action that was performed.
	 * @return The action that was performed.
	 */
	public void setAction(Integer action) {
		this.action = action;
	}

	/**
	 * Get the ID of the object that the action was performed on.
	 * @return The ID of the object that the action was performed on.
	 */
	public Integer getAssociatedId() {
		return associatedId;
	}

	/**
	 * Set the ID of the object that the action was performed on.
	 * @return The ID of the object that the action was performed on.
	 */
	public void setAssociatedId(Integer associatedId) {
		this.associatedId = associatedId;
	}

	/**
	 * Get the note associated with the action.
	 * @return The note associated with the action.
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Set the note associated with the action.
	 * @return The note associated with the action.
	 */
	public void setNote(String note) {
		this.note = note;
	}
}
