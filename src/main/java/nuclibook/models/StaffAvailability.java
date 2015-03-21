package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

/**
 * Model that represents a time instance in which the staff member will be usually available.
 */
@DatabaseTable(tableName = "staff_availabilities")
public class StaffAvailability implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id", foreignAutoRefresh = true)
	private Staff staff;

	@DatabaseField
	private int day;

	@DatabaseField(columnName = "start_time")
	private int startTime;

	@DatabaseField(columnName = "end_time")
	private int endTime;

    /**
     * Gets the ID of the staff availability.
     * @return the ID of the staff availability.
     */
	public Integer getId() {
		return id;
	}

    /**
     * Sets the ID of the staff availability.
     * @param id the ID of the staff availability.
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Get the staff member who this availability is about.
     * @return the staff member who this availability is about.
     */
	public Staff getStaff() {
		return staff;
	}

    /**
     * Sets the staff member who this availability is about.
     * @param staff the staff member who this availability is about.
     */
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

    /**
     * Gets the day of the week in which the staff will be available.
     * @return the day of the week in which the staff will be available.
     */
	public int getDay() {
		return day;
	}

    /**
     * Sets the day of the week in which the staff will be available.
     * @param day the day of the week in which the staff will be available.
     */
	public void setDay(int day) {
		this.day = day;
	}

    /**
     * Gets the time of day in which the staff will be available.
     * @return the time of day in which the staff will be available.
     */
	public TimeOfDay getStartTime() {
		try {
			return new TimeOfDay(startTime);
		} catch (InvalidTimeOfDayException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Sets the time of day in which the staff will be available.
     * @param startTime the time of day in which the staff will be available.
     */
	public void setStartTime(TimeOfDay startTime) {
		this.startTime = startTime.getSecondsPastMidnight();
	}

    /**
     * Gets the time of day in which the staff will no longer be available.
     * @return the time of day in which the staff will no longer be available.
     */
	public TimeOfDay getEndTime() {
		try {
			return new TimeOfDay(endTime);
		} catch (InvalidTimeOfDayException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Sets the time of day in which the staff will no longer be available.
     * @param endTime the time of day in which the staff will no longer be available.
     */
	public void setEndTime(TimeOfDay endTime) {
		this.endTime = endTime.getSecondsPastMidnight();
	}

	@Override
	public HashMap<String, String> getHashMap() {
		String[] dayLabels = new String[]{"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		return new HashMap<String, String>() {{
			put("id", getId().toString());
			put("day-of-week", getDay() + "");
			put("day-of-week-label", dayLabels[getDay()]);
			put("start-time", (getStartTime().getHour() < 10 ? "0" : "") + getStartTime().getHour() + ":" + (getStartTime().getMinute() < 10 ? "0" : "") + getStartTime().getMinute());
			put("end-time", (getEndTime().getHour() < 10 ? "0" : "") + getEndTime().getHour() + ":" + (getEndTime().getMinute() < 10 ? "0" : "") + getEndTime().getMinute());
		}};
	}
}
