package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

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

    public StaffAvailability() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public TimeOfDay getStartTime() {
        try {
            return new TimeOfDay(startTime);
        } catch (InvalidTimeOfDayException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setStartTime(TimeOfDay startTime) {
        this.startTime = startTime.getSecondsPastMidnight();
    }

    public TimeOfDay getEndTime() {
        try {
            return new TimeOfDay(endTime);
        } catch (InvalidTimeOfDayException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setEndTime(TimeOfDay endTime) {
        this.endTime = endTime.getSecondsPastMidnight();
    }

    @Override
    public HashMap<String, String> getHashMap() {
        String[] dayLabels = new String[] {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("day-of-week", getDay() + "");
            put("day-of-week-label", dayLabels[getDay()]);
            put("start-time", getStartTime().getHour() + ":" + getStartTime().getMinute());
            put("end-time", getEndTime().getHour() + ":" + getEndTime().getMinute());
        }};
    }
}
