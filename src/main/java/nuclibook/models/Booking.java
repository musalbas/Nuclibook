package nuclibook.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

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

    public Booking() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Therapy getTherapy() {
		return therapy;
	}

	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public DateTime getStart() {
		return new DateTime(start);
	}

	public void setStart(DateTime start) {
		this.start = start.toString();
	}

	public DateTime getEnd() {
		return new DateTime(end);
	}

	public void setEnd(DateTime end) {
		this.end = end.toString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
