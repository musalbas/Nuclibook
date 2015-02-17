package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "permissions")
public class Permission {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 32)
    private String label;

    @DatabaseField(width = 64)
    private String description;

    public Permission(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public Permission() { //Empty constructor for ORMLite
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
