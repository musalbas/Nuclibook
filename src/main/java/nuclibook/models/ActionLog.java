package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "action_log")
public class ActionLog {

	@DatabaseField(generatedId = true)
	private Integer id;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
