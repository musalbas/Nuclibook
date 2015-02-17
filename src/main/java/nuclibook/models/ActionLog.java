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

    @DatabaseField
    private Integer associatedID;

    @DatabaseField
    private String note;

    public ActionLog(Staff staff) {
        this.staff = staff;
    }

    public ActionLog() {
        // Empty constructor for ORMLite
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff, int action, int associatedID) {

        this.staff = staff;
        this.action = action;
        this.associatedID = associatedID;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAction() {
        return this.action;
    }

    public Integer getAssociatedID() {
        return this.associatedID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

}
