package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "therapies")
public class Therapy {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(width = 64)
    private String name;
    @DatabaseField(defaultValue = "2")
    private int duration;
    @DatabaseField(columnName = "medicine_required", foreign = true)
    private Medicine medicineRequired;
    @DatabaseField(width = 32, columnName = "medicine_dose")
    private String medicineDose;
    @DatabaseField(columnName = "camera_type_required", foreign = true)
    private CameraType cameraTypeRequired;


}
