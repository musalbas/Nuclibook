package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

/**
 * Model to represent a camera type.
 */
@DatabaseTable(tableName = "camera_types")
public class CameraType implements Renderable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 64)
    private String label;

    @DatabaseField(defaultValue = "true")
    private Boolean enabled;

    /**
     * Gets the ID of the camera type.
     *
     * @return the ID of the camera type.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the camera type.
     *
     * @param id the ID of the camera type.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the label of the camera type.
     *
     * @return the label of the camera type.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the camera type.
     *
     * @param label the label of the camera type.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets whether it's enabled or not.
     *
     * @return whether it's enabled or not.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether it's enabled or not.
     *
     * @param enabled whether it's enabled or not.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("label", getLabel());
        }};
    }
}
