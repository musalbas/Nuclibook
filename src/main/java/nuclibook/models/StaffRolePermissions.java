package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "staff_role_permissions")
public class StaffRolePermissions {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staff;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "permission_id")
    private Permission permission;

    public StaffRolePermissions() { //Empty constructor for ORMLite
    }

    public StaffRolePermissions(Staff staff, Permission permission) {
        this.staff = staff;
        this.permission = permission;
    }

    public Integer getId() {
        return id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
