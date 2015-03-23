package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

/**
 * Model to represent a camera.
 */
@DatabaseTable(tableName = "cameras")
public class Camera implements Renderable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "camera_type_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private CameraType type;

    @DatabaseField(canBeNull = false, columnName = "room_number")
    private String roomNumber;

    @DatabaseField(defaultValue = "true")
    private Boolean enabled;

	/**
	 * Blank constructor for ORM.
	 */
	public Camera() {
	}

    /**
     * Gets the ID of the camera.
     *
     * @return the ID of the camera.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the camera.
     *
     * @param id the ID of the camera.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the type of camera.
     *
     * @return the type of camera.
     */
    public CameraType getType() {
        return type;
    }

    /**
     * Sets the type of camera.
     *
     * @param type the type of camera.
     */
    public void setType(CameraType type) {
        this.type = type;
    }

    /**
     * Gets the camera's room number.
     *
     * @return the camera's room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the camera's room number.
     *
     * @param roomNumber the camera's room number.
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets whether the camera is available.
     *
     * @return whether the camera is available.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether the camera is available.
     *
     * @param enabled whether the camera is available.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("camera-id", getId().toString());
            put("camera-id-all", getId().toString());
            put("camera-type-id", getType().getId().toString());
            put("camera-type-label", getType().getLabel());
            put("room-number", getRoomNumber());
        }};
    }
}
