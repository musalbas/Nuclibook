package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

@DatabaseTable(tableName = "camera_types")
public class CameraType implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String label;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	public CameraType() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
			put("label", getLabel());
		}};
	}
}
