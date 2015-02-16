package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "staff_role")
public class StaffRole {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(width = 32)
    private String label;
}
