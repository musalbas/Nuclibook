package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

@DatabaseTable(tableName = "tracers")
public class PatientQuestions implements Renderable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 256)
    private String description;

    @DatabaseField(columnName = "therapy_required", foreign = true, foreignAutoRefresh = true)
    private Therapy therapyRequired;

    public PatientQuestions() {
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

    public Therapy getTherapyRequired() {
        return therapyRequired;
    }

    public void setTherapyRequired(Therapy therapyRequired) {
        this.therapyRequired = therapyRequired;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>(){{
            put("id", getId().toString());
            put("description", getDescription());
            put("therapy-required", getTherapyRequired().toString());
        }};
    }
}
