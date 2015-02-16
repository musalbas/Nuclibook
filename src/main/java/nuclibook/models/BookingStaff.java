package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "booking_staff")
public class BookingStaff {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "booking_id")
    private Booking bookingID;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staffID;
}
