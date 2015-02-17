package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;

public class StaffAvailabilities {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "availability_id")
    private Availability permissionID;

}
