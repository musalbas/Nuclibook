package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "staff_role_permissions")
public class StaffRolePermissions {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "permission_id")
    private Permission permissionID;
}
