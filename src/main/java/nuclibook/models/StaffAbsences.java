package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "staff_absences")
public class StaffAbsences {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;
    @DatabaseField
    private Date from;
    @DatabaseField
    private Date to;

    public StaffAbsences(Staff staffID, Date from, Date to) {
        this.staffID = staffID;
        this.from = from;
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public Staff getStaffID() {
        return staffID;
    }

    public void setStaffID(Staff staffID) {
        this.staffID = staffID;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
