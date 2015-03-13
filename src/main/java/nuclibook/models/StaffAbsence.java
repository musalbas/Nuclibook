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

	@DatabaseField(canBeNull = false, foreign = true, columnName = "staff_id", foreignAutoRefresh = true)
	private Staff staff;

	@DatabaseField
	private long from;

	@DatabaseField
	private long to;

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
		this.from = from.getMillis();
	}

	public DateTime getTo() {
		return new DateTime(to);
	}

	public void setTo(DateTime to) {
		this.to = to.getMillis();
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("id", getId().toString());
			put("from", getFrom().toString("YYYY-MM-dd HH:mm"));
			put("to", getTo().toString("YYYY-MM-dd HH:mm"));
		}};
	}
}
