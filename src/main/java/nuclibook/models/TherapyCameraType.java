package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model to represent the relationship between camera types and therapies.
 */
@DatabaseTable(tableName = "therapy_camera")
public class TherapyCameraType {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "therapy_id", foreignAutoRefresh = true)
	private Therapy therapy;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "camera_type_id", foreignAutoRefresh = true)
	private CameraType cameraType;

    /**
     * Initialise a relationship without the fields.
     */
	public TherapyCameraType() {
	}

    /**
     * Initialise a relationship with a therapy and a camera type.
     * @param therapy the therapy object
     * @param cameraType the camera type object
     */
	public TherapyCameraType(Therapy therapy, CameraType cameraType) {
		this.therapy = therapy;
		this.cameraType = cameraType;
	}

    /**
     * Gets the ID of the relationship.
     * @return the ID of the relationship.
     */
	public Integer getId() {
		return id;
	}

    /**
     * Sets the ID of the relationship
     * @param id the ID of the relationship
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Gets the camera type.
     * @return the camera type.
     */
	public CameraType getCameraType() {
		return cameraType;
	}

    /**
     * Sets the camera type.
     * @param cameraType the camera type.
     */
	public void setCameraType(CameraType cameraType) {
		this.cameraType = cameraType;
	}

    /**
     * Gets the therapy.
     * @return the therapy.
     */
	public Therapy getTherapy() {
		return therapy;
	}

    /**
     * Sets the therapy.
     * @param therapy the therapy.
     */
	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
	}
}
