package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

@DatabaseTable(tableName = "therapies")
public class Therapy implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String name;

	@DatabaseField(defaultValue = "60")
	private int duration;

	@DatabaseField(columnName = "tracer_required", foreign = true, foreignAutoRefresh = true)
	private Tracer tracerRequired;

	@DatabaseField(width = 32, columnName = "tracer_dose")
	private String tracerDose;

	@DatabaseField(columnName = "camera_type_required", foreign = true, foreignAutoRefresh = true)
	private CameraType cameraTypeRequired;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	public Therapy() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Tracer getTracerRequired() {
		return tracerRequired;
	}

	public void setTracerRequired(Tracer tracerRequired) {
		this.tracerRequired = tracerRequired;
	}

	public String getTracerDose() {
		return tracerDose;
	}

	public void setTracerDose(String tracerDose) {
		this.tracerDose = tracerDose;
	}

	public CameraType getCameraTypeRequired() {
		return cameraTypeRequired;
	}

	public void setCameraTypeRequired(CameraType cameraTypeRequired) {
		this.cameraTypeRequired = cameraTypeRequired;
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
			put("name", getName());
			put("default-duration", ((Integer) getDuration()).toString());
			put("tracer-required-id", getTracerRequired().getId().toString());
			put("tracer-required-name", getTracerRequired().getName());
			put("tracer-dose", getTracerDose());
			put("camera-type-id", getCameraTypeRequired().getId().toString());
			put("camera-type-label", getCameraTypeRequired().getLabel());
		}};
	}
}
