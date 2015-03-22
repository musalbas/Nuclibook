package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

/**
 * Model to represent a question to ask a patient before a therapy is booked.
 */
@DatabaseTable(tableName = "patient_questions")
public class PatientQuestion implements Renderable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 256)
    private String description;

    @DatabaseField(columnName = "therapy_id", foreign = true, foreignAutoRefresh = true)
    private Therapy therapy;

    @DatabaseField
    private int sequence;

    /**
     * Gets the ID of the question.
     *
     * @return the ID of the question.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the question.
     *
     * @param id the ID of the question.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the description of the question.
     *
     * @return the description of the question.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the question.
     *
     * @param description the description of the question.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the therapy the question is about.
     *
     * @return the therapy the question is about.
     */
    public Therapy getTherapy() {
        return therapy;
    }

    /**
     * Sets the therapy the question is about.
     *
     * @param therapy the therapy the question is about.
     */
    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    /**
     * Gets the ordering of the question among the list of questions.
     *
     * @return the ordering of the question among the list of questions.
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * Sets the ordering of the question among the list of questions.
     *
     * @param sequence the ordering of the question among the list of questions.
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("description", getDescription());
        }};
    }
}
