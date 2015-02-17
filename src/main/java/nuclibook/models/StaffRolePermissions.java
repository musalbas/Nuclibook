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

    public StaffRolePermissions() { //Empty constructor for ORMLite
    }

    public StaffRolePermissions(Staff staffID, Permission permissionID) {
        this.staffID = staffID;
        this.permissionID = permissionID;
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

    public Permission getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Permission permissionID) {
        this.permissionID = permissionID;
    }
}
