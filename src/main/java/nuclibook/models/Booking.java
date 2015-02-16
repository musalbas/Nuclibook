package nuclibook.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

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
    private Date start;
    @DatabaseField
    private Date end;
    @DatabaseField(width = 16)
    private String status;
    @DatabaseField(dataType = DataType.LONG_STRING)
    private String notes;
}
