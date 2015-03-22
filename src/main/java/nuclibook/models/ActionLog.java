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

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "staff_id")
    private Staff staff;

    @DatabaseField(columnName = "action_id")
    private Integer actionId;

    @DatabaseField(canBeNull = true, columnName = "associated_id")
    private Integer associatedId;

    @DatabaseField
    private String note;

	/**
	 * Blank constructor for ORM.
	 */
	public ActionLog() {
	}

	/**
     * Initialise action log.
     *
     * @param staff        Staff who performed the action.
     * @param when         Date the action was performed.
     * @param actionId     The action (constant from entity_utils.ActionLogger).
     * @param associatedId The ID of the object that the action was performed on.
     */
    public ActionLog(Staff staff, DateTime when, Integer actionId, Integer associatedId) {
        this.staff = staff;
        this.actionId = actionId;
        this.associatedId = associatedId;
        this.when = when.getMillis();
    }

	/**
	 * Initialise action log.
	 *
	 * @param staff        Staff who performed the action.
	 * @param when         Date the action was performed.
	 * @param actionId     The action (constant from entity_utils.ActionLogger).
	 * @param associatedId The ID of the object that the action was performed on.
	 * @param note         A note associated this this event.
	 */
    public ActionLog(Staff staff, DateTime when, Integer actionId, Integer associatedId, String note) {
        this.staff = staff;
        this.actionId = actionId;
        this.associatedId = associatedId;
        this.note = note;
        this.when = when.getMillis();
    }

    /**
     * Get the ID of the action.
     *
     * @return The ID of the action.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the ID of the action.
     *
     * @param id The ID of the action.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the date the action was performed.
     *
     * @return The date the action was performed.
     */
    public DateTime getWhen() {
        return new DateTime(when);
    }

    /**
     * Get the member of staff who performed the action.
     *
     * @return The member of staff who performed the action.
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * Set the member of staff who performed the action.
     *
     * @param staff The member of staff who performed the action.
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    /**
     * Get the action that was performed.
     *
     * @return The action that was performed.
     */
    public Integer getActionId() {
        return actionId;
    }

    /**
     * Get the ID of the object that the action was performed on.
     *
     * @return The ID of the object that the action was performed on.
     */
    public Integer getAssociatedId() {
        return associatedId;
    }

    /**
     * Get the note associated with the action.
     *
     * @return The note associated with the action.
     */
    public String getNote() {
        return note;
    }

}
