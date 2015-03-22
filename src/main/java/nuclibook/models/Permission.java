package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

/**
 * Model that represents a permission that allows a user to
 * accomplish a task on the application, or gain access to
 * a part of the system.
 */
@DatabaseTable(tableName = "permissions")
public class Permission implements Renderable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 32)
    private String label;

    @DatabaseField(width = 64)
    private String description;

	/**
	 * Blank constructor for ORM.
	 */
	public Permission() {
	}

    /**
     * Gets the ID of the permission.
     *
     * @return the ID of the permission.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the label of the permission.
     *
     * @return the label of the permission.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the description of the permission.
     *
     * @return the description of the permission.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("label", getLabel());
            put("description", getDescription());
        }};
    }
}
