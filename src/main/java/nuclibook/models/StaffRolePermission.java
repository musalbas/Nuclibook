package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model to represent the relationship between a staff role and a permission,
 */
@DatabaseTable(tableName = "staff_role_permissions")
public class StaffRolePermission {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_role_id", foreignAutoRefresh = true)
    private StaffRole staffRole;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "permission_id", foreignAutoRefresh = true)
    private Permission permission;

	/**
	 * Blank constructor for ORM.
	 */
	public StaffRolePermission() {
	}

    /**
     * Initialise a relationship with a StaffRole object and a permission.
     *
     * @param staffRole  the staff role object.
     * @param permission a permission.
     */
    public StaffRolePermission(StaffRole staffRole, Permission permission) {
        this.staffRole = staffRole;
        this.permission = permission;
    }

    /**
     * Gets the ID of the staff role and permission relationship.
     *
     * @return the ID of the staff role and permission relationship.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the staff role and permission relationship.
     *
     * @param id the ID of the staff role and permission relationship.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the permission.
     *
     * @return the permission.
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the permission.
     *
     * @param permission the permission.
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
