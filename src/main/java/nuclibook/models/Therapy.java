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

	@DatabaseField(columnName = "medicine_required", foreign = true, foreignAutoRefresh = true)
	private Medicine medicineRequired;

	@DatabaseField(width = 32, columnName = "medicine_dose")
	private String medicineDose;

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

	public Medicine getMedicineRequired() {
		return medicineRequired;
	}

	public void setMedicineRequired(Medicine medicineRequired) {
		this.medicineRequired = medicineRequired;
	}

	public String getMedicineDose() {
		return medicineDose;
	}

	public void setMedicineDose(String medicineDose) {
		this.medicineDose = medicineDose;
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
			put("medicine-required-id", getMedicineRequired().getId().toString());
			put("medicine-required-name", getMedicineRequired().getName());
			put("medicine-dose", getMedicineDose());
			put("camera-type-id", getCameraTypeRequired().getId().toString());
			put("camera-type-label", getCameraTypeRequired().getLabel());
		}};
	}
}
