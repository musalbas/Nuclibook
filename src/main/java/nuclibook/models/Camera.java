package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

@DatabaseTable(tableName = "cameras")
public class Camera implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(columnName = "camera_type_id", canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private CameraType type;

	@DatabaseField(canBeNull = false)
	private String roomNumber;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	public Camera() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CameraType getType() {
		return type;
	}

	public void setType(CameraType type) {
		this.type = type;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>(){{
			put("id", getId().toString());
			put("camera-id", getId().toString());
			put("camera-type-id", getType().getId().toString());
			put("camera-type-label", getType().getLabel());
			put("room-number", getRoomNumber());
		}};
	}
}
