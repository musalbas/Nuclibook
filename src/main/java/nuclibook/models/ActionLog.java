package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "action_log")
public class ActionLog {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;

    @DatabaseField
    private Integer action;

    @DatabaseField
    private Integer associatedID;

    @DatabaseField
    private String note;

    public ActionLog(Staff staffID) {
        this.staffID = staffID;
    }

    public ActionLog() {
        // Empty constructor for ORMLite
    }

    public Staff getStaffID() {
        return staffID;
    }

    public void setStaffID(Staff staffID) {
        this.staffID = staffID;
    }

    public Integer getId() {
        return id;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getAction() {
        return this.action;
    }

    public void setAssociatedID(Integer associatedID) {
        this.associatedID = associatedID;
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
