package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;

import java.util.HashMap;

@DatabaseTable(tableName = "generic_events")
public class GenericEvent implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String title;

	@DatabaseField
	private String description;

	@DatabaseField
	private long from;

	@DatabaseField
	private long to;

	public GenericEvent() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

			put("event-title", getTitle());
			put("description", getDescription());

			put("from", getFrom().toString("YYYY-MM-dd HH:mm"));
			put("from-date", getFrom().toString("YYYY-MM-dd"));
			put("from-time", getFrom().toString("HH:mm"));

			put("to", getTo().toString("YYYY-MM-dd HH:mm"));
			put("to-date", getTo().toString("YYYY-MM-dd"));
			put("to-time", getTo().toString("HH:mm"));
		}};
	}
}
