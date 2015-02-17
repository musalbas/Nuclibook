package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "action_log")
public class ActionLog {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;

    public ActionLog(Staff staffID) {
        this.staffID = staffID;
    }

    public ActionLog() { //Empty constructor for ORMLite
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
}
