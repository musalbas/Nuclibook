package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;

public class StaffAvailabilities {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
    private Staff staff;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "availability_id")
    private Availability availability;

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getStaff() {
        return this.staff;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Availability getAvailability() {
        return this.availability;
    }

}
