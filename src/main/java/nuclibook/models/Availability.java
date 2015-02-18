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

    @DatabaseField(columnName = "start_time")
    private int startTime;

    @DatabaseField(columnName = "end_time")
    private int endTime;

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
		try {
			return new TimeOfDay(startTime);
		} catch (InvalidTimeOfDayException e) {
			return null;
		}
	}

	public void setStartTime(TimeOfDay startTime) {
		this.startTime = startTime.getSecondsPastNight();
	}

	public TimeOfDay getEndTime() {
		try {
			return new TimeOfDay(endTime);
		} catch (InvalidTimeOfDayException e) {
			return null;
		}
	}

	public void setEndTime(TimeOfDay endTime) {
		this.endTime = endTime.getSecondsPastNight();
	}
}
