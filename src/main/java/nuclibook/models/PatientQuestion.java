package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

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

    public PatientQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Therapy getTherapy() {
        return therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>(){{
            put("id", getId().toString());
            put("description", getDescription());
        }};
    }
}
