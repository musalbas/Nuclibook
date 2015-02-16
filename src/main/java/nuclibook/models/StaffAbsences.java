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

}
