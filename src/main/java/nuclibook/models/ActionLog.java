package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "action_log")
public class ActionLog {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private String when;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
	private Staff staff;

	@DatabaseField
	private Integer action;

	@DatabaseField(canBeNull = true, columnName = "associated_id")
	private Integer associatedId;

	@DatabaseField
	private String note;

	public ActionLog() {
	}

	public ActionLog(Staff staff, DateTime when, Integer action, Integer associatedId) {
		this.staff = staff;
		this.action = action;
		this.associatedId = associatedId;
		setWhen(when);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DateTime getWhen() {
		return new DateTime(when);
	}

	public void setWhen(DateTime when) {
		this.when = when.toString();
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(Integer associatedId) {
		this.associatedId = associatedId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
