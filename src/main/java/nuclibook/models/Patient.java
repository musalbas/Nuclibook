package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Model to represent a patient.
 */
@DatabaseTable(tableName = "patients")
public class Patient implements Renderable, Exportable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 64)
    private String name;

    @DatabaseField(width = 64, columnName = "hospital_number")
    private String hospitalNumber;

    @DatabaseField(width = 64, columnName = "nhs_number")
    private String nhsNumber;

    @DatabaseField(columnName = "date_of_birth")
    private long dateOfBirth;

    @DatabaseField(width = 6)
    private String sex;

    @DatabaseField(defaultValue = "true")
    private Boolean enabled;

    /**
     * Gets the ID of the patient.
     *
     * @return the ID of the patient.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the patient.
     *
     * @param id the ID of the patient.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the patient.
     *
     * @return the name of the patient.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the patient.
     *
     * @param name the name of the patient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the patient's hospital number.
     *
     * @return the patient's hospital number.
     */
    public String getHospitalNumber() {
        return hospitalNumber;
    }

    /**
     * Sets the patient's hospital number.
     *
     * @param hospitalNumber the patient's hospital number.
     */
    public void setHospitalNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
    }

    /**
     * Gets the patient's NHS number.
     *
     * @return the patient's NHS number.
     */
    public String getNhsNumber() {
        return nhsNumber;
    }

    /**
     * Sets the patient's NHS number.
     *
     * @param nhsNumber the patient's NHS number.
     */
    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    /**
     * Gets the patient's birthdate.
     *
     * @return the patient's birthdate.
     */
    public DateTime getDateOfBirth() {
        return new DateTime(dateOfBirth);
    }

    /**
     * Sets the patient's birthdate.
     *
     * @param dateOfBirth the patient's birthdate.
     */
    public void setDateOfBirth(DateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth.getMillis();
    }

    /**
     * Gets the patient's sex.
     *
     * @return the patient's sex.
     */
    public Sex getSex() {
        return (sex != null && sex.equals("MALE")) ? Sex.MALE : Sex.FEMALE;
    }

    /**
     * Sets the patient's sex.
     *
     * @param sex the patient's sex.
     */
    public void setSex(Sex sex) {
        this.sex = sex.toString();
    }

    /**
     * Gets whether it's enabled in the database.
     *
     * @return whether it's enabled in the database.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether it's enabled in the database.
     *
     * @param enabled whether it's enabled in the database.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("name", getName());
            put("hospital-number", getHospitalNumber());
            put("nhs-number", getNhsNumber());
            put("date-of-birth", getDateOfBirth().toString("YYYY-MM-dd"));
            put("sex", getSex() == Sex.MALE ? "Male" : "Female");
            put("sex-code", getSex() == Sex.MALE ? "M" : "F");
        }};
    }

    /**
     * Returns an exportable <code>HashMap</code> of the overridden method getHashMap.
     *
     * @return
     */
    public HashMap<String, String> getExportableHashMap() {
        return getHashMap();
    }

    /**
     * An enumerated type that defines the user's sex.
     */
    public enum Sex {
        MALE, FEMALE
    }
}
