package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "therapies")
public class Therapy {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String name;

	@DatabaseField(defaultValue = "60")
	private int duration;

	@DatabaseField(columnName = "medicine_required", foreign = true)
	private Medicine medicineRequired;

	@DatabaseField(width = 32, columnName = "medicine_dose")
	private String medicineDose;

	@DatabaseField(columnName = "camera_type_required", foreign = true)
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
}
