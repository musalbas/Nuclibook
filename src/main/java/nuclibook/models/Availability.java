package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Time;
import java.time.DayOfWeek;

@DatabaseTable(tableName = "availabilities")
public class Availability {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private DayOfWeek day;

    @DatabaseField
    private TimeOfDay startTime;

    @DatabaseField
    private TimeOfDay endTime;

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public DayOfWeek getDay() {
        return this.day;
    }

    public void setStartTime(TimeOfDay startTime) {
        this.startTime = startTime;
    }

    public TimeOfDay getStartTime() {
        return this.startTime;
    }

    public void setEndTime(TimeOfDay endTime) {
        this.endTime = endTime;
    }

    public TimeOfDay getEndTime(TimeOfDay endTime) {
        return this.endTime;
    }

}
