package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "staff_role_permissions")
public class StaffRolePermission {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_role_id", foreignAutoRefresh = true)
	private StaffRole staffRole;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "permission_id", foreignAutoRefresh = true)
	private Permission permission;

	public StaffRolePermission() {
	}

	public StaffRolePermission(StaffRole staffRole, Permission permission) {
		this.staffRole = staffRole;
		this.permission = permission;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StaffRole getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(StaffRole staffRole) {
		this.staffRole = staffRole;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
