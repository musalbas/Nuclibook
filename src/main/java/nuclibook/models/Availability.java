package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public TimeOfDay getStartTime() {
		return startTime;
	}

	public void setStartTime(TimeOfDay startTime) {
		this.startTime = startTime;
	}

	public TimeOfDay getEndTime() {
		return endTime;
	}

	public void setEndTime(TimeOfDay endTime) {
		this.endTime = endTime;
	}
}
