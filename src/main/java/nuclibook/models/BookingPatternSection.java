package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model to represent a section of the pattern for a booking.
 */
@DatabaseTable(tableName = "booking_pattern_sections")
public class BookingPatternSection {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "therapy_id", foreignAutoRefresh = true)
    private Therapy therapy;

    @DatabaseField
    private boolean busy;

    @DatabaseField
    private int minLength;

    @DatabaseField
    private int maxLength;

    @DatabaseField
    private int sequence;

	/**
	 * Blank constructor for ORM.
	 */
	public BookingPatternSection() {
	}

    /**
     * Get the ID of the booking pattern section.
     *
     * @return the ID of the booking pattern section.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the ID of the booking pattern section.
     *
     * @param id the ID of the booking pattern section.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the therapy which this pattern's booking represents.
     *
     * @return the therapy which this pattern's booking represents.
     */
    public Therapy getTherapy() {
        return therapy;
    }

    /**
     * Set the therapy which this pattern's booking represents.
     *
     * @param therapy the therapy which this pattern's booking represents.
     */
    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    /**
     * Checks if the section is busy or free.
     *
     * @return whether the section is busy or free.
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * Sets whether the section is busy or free.
     *
     * @param busy whether the section is busy or free.
     */
    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    /**
     * Gets the minimum length (in minutes) of a section.
     *
     * @return the minimum length (in minutes) of a section.
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Sets the minimum the minimum length (in minutes) of a section.
     *
     * @param minLength the minimum length (in minutes) of a section.
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    /**
     * Gets the maximum length (in minutes) of a section.
     *
     * @return the maximum length (in minutes) of a section.
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the minimum the maximum length (in minutes) of a section.
     *
     * @param maxLength the maximum length (in minutes) of a section.
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Gets the ordering of the section.
     *
     * @return the ordering of the section.
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * Sets the ordering of the section.
     *
     * @param sequence the ordering of the section.
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
