package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Model to represent a generic event, such as camera maintenance.
 */
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

    /**
     * Initialises the event without any fields.
     */
	public GenericEvent() {
	}

    /**
     * Gets the ID of the event.
     * @return the ID of the event.
     */
	public Integer getId() {
		return id;
	}

    /**
     * Sets the ID of the event.
     * @param id the ID of the event.
     */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * Gets the title of the event.
     * @return the title of the event.
     */
	public String getTitle() {
		return title;
	}

    /**
     * Sets the title of the event.
     * @param title the title of the event.
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Gets the description of the event.
     * @return the description of the event.
     */
	public String getDescription() {
		return description;
	}

    /**
     * Sets the description of the event.
     * @param description the description of the event.
     */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Gets the starting time of the event.
     * @return the starting time of the event.
     */
	public DateTime getFrom() {
		return new DateTime(from);
	}

    /**
     * Sets the starting time of the event.
     * @param from the starting time of the event.
     */
	public void setFrom(DateTime from) {
		this.from = from.getMillis();
	}

    /**
     * Gets the end time of the event.
     * @return the end time of the event.
     */
	public DateTime getTo() {
		return new DateTime(to);
	}

    /**
     * Sets the end time of the event.
     * @param to the end time of the event.
     */
	public void setTo(DateTime to) {
		this.to = to.getMillis();
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("id", getId().toString());

			put("title", getTitle());
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
