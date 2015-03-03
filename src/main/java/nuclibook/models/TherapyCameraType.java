package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "therapy_cameras")
public class TherapyCameraType {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "therapy_id", foreignAutoRefresh = true)
	private Therapy therapy;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "camera_id", foreignAutoRefresh = true)
	private CameraType cameraType;

	public TherapyCameraType() {
	}

	public TherapyCameraType(Therapy therapy, CameraType cameraType) {
		this.therapy = therapy;
		this.cameraType = cameraType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CameraType getCameraType() {
		return cameraType;
	}

	public void setCameraType(CameraType cameraType) {
		this.cameraType = cameraType;
	}

	public Therapy getTherapy() {
		return therapy;
	}

	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
	}
}
