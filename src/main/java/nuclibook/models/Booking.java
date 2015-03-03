package nuclibook.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

/**
 * Model representing a booking.
 */
@DatabaseTable(tableName = "bookings")
public class Booking {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "patient_id", foreign = true)
    private Patient patient;

    @DatabaseField(columnName = "therapy", foreign = true)
    private Therapy therapy;

    @DatabaseField(columnName = "camera", foreign = true)
    private Camera camera;

    @DatabaseField
    private String start;

    @DatabaseField
    private String end;

    @DatabaseField(width = 16)
    private String status;

    @DatabaseField(dataType = DataType.LONG_STRING)
    private String notes;

	/**
	 * Initialise a booking.
	 */
    public Booking() {
    }

	/**
	 * Get the ID of the booking.
	 * @return The ID of the booking.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Set the ID of the booking.
	 * @return The ID of the booking.
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Get the patient.
	 * @return The patient.
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Set the patient.
	 * @return The patient.
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Get the therapy.
	 * @return The therapy.
	 */
	public Therapy getTherapy() {
		return therapy;
	}

	/**
	 * Set the therapy.
	 * @return The therapy.
	 */
	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
	}

	/**
	 * Get the camera.
	 * @return The camera.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Set the camera
	 * @param camera The camera.
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * Get the booking start time.
	 * @return The booking start time.
	 */
	public DateTime getStart() {
		return new DateTime(start);
	}

	/**
	 * Set the booking start time.
	 * @param start The booking start time.
	 */
	public void setStart(DateTime start) {
		this.start = start.toString();
	}

	/**
	 * Get the booking end time.
	 * @return The booking end time.
	 */
	public DateTime getEnd() {
		return new DateTime(end);
	}

	/**
	 * Set the booking end time.
	 * @param end The booking end time.
	 */
	public void setEnd(DateTime end) {
		this.end = end.toString();
	}

	/**
	 * Get the status of the booking.
	 * @return The status of the booking.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the booking
	 * @param status The status of the booking.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get the note associated with the booking.
	 * @return The note associated with the booking.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the note associated with the booking.
	 * @return The note associated with the booking.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
