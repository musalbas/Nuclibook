package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "camera_type")
public class CameraType{

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(width = 64)
    private String label;

    public CameraType(String label) {
        this.label = label;
    }

    public CameraType() { //Empty constructor for ORMLite
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }
}
