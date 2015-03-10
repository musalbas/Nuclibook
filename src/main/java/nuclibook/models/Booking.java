package nuclibook.models;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	@ForeignCollectionField(eager = true)
	private ForeignCollection<BookingSection> bookingSections;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<BookingStaff> bookingStaff;

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
	 * @param id The ID of the booking.
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
	 * @param patient The patient.
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
	 * @param therapy The therapy.
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
	 * Get the list of booking sections for this booking.
	 * @return The list of booking sections for this booking.
	 */
	public List<BookingSection> getBookingSections() {
		ArrayList<BookingSection> output = new ArrayList<>();
		try {
			bookingSections.refreshCollection();
		} catch (SQLException | NullPointerException e) {
			return output;
		}
		CloseableIterator<BookingSection> iterator = bookingSections.closeableIterator();
		try {
			BookingSection bs;
			while (iterator.hasNext()) {
				bs = iterator.next();
				if (bs != null) output.add(bs);
			}
		} finally {
			iterator.closeQuietly();
		}
		return output;
	}

	/**
	 * Get the list of staff for this booking.
	 * @return The list of staff for this booking.
	 */
	public List<Staff> getStaff() {
		ArrayList<Staff> output = new ArrayList<>();
		try {
			bookingStaff.refreshCollection();
		} catch (SQLException | NullPointerException e) {
			return output;
		}
		CloseableIterator<BookingStaff> iterator = bookingStaff.closeableIterator();
		try {
			BookingStaff bs;
			while (iterator.hasNext()) {
				bs = iterator.next();
				if (bs != null) output.add(bs.getStaff());
			}
		} finally {
			iterator.closeQuietly();
		}
		return output;
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
	 * Get the notes associated with the booking.
	 * @return The notes associated with the booking.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the notes associated with the booking.
	 * @param notes The notes associated with the booking.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
