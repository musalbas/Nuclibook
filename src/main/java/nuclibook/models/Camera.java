package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javax.persistence.OneToOne;

@DatabaseTable(tableName = "cameras")
public class Camera {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    private CameraType type;
    @DatabaseField(width = 32, columnName = "room_number")
    private String roomNumber;

    public Camera(CameraType type, String roomNumber) {
        this.type = type;
        this.roomNumber = roomNumber;
    }

    public Camera(){ //Empty constructor for ORMLite
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public CameraType getType() {
        return type;
    }

    public void setType(CameraType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

}
