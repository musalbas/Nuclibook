package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;

import java.util.HashMap;

@DatabaseTable(tableName = "staff_absences")
public class StaffAbsence implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id")
	private Staff staff;

	@DatabaseField
	private String from;

	@DatabaseField
	private String to;

	public StaffAbsence() {
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

	public DateTime getFrom() {
		return new DateTime(from);
	}

	public void setFrom(DateTime from) {
		this.from = from.toString();
	}

	public DateTime getTo() {
		return new DateTime(to);
	}

	public void setTo(DateTime to) {
		this.to = to.toString();
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("id", getId().toString());
			put("from", getFrom().toString());
			put("to", getTo().toString());
		}};
	}
}
