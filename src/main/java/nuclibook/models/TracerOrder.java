package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.HashMap;

@DatabaseTable(tableName = "tracer_orders")
public class TracerOrder implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(foreign = true, columnName = "tracer_id", foreignAutoRefresh = true)
	private Tracer tracer;

	@DatabaseField(width = 32, columnName = "tracer_dose")
	private String tracerDose;

	@DatabaseField(foreign = true, columnName = "booking_id", foreignAutoRefresh = true)
	private Booking booking;

	@DatabaseField
	private long dateRequired;

	@DatabaseField
	private boolean done;

	public TracerOrder() {
	}

	public Integer getId() {
		return id;
	}

	public Tracer getTracer() {
		return tracer;
	}

	public void setTracer(Tracer tracer) {
		this.tracer = tracer;
	}

	public String getTracerDose() {
		return tracerDose;
	}

	public void setTracerDose(String tracerDose) {
		this.tracerDose = tracerDose;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public DateTime getDateRequired() {
		return new DateTime(dateRequired);
	}

	public void setDateRequired(DateTime dateRequired) {
		this.dateRequired = dateRequired.getMillis();
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("tracer-order-id", getId().toString());
			put("tracer-id", getTracer().getId().toString());
			put("tracer-name", getTracer().getName());
			put("tracer-dose", getTracerDose());
			put("date-required", getDateRequired().toString("YYYY-MM-dd HH:mm"));
			put("done", isDone() ? "1" : "0");

			// put days until string
			int daysUntil = Days.daysBetween(new LocalDate(), getDateRequired().toLocalDate()).getDays();
			if (daysUntil == 0) {
				put("days-until", "today");
			} else if (daysUntil < 0) {
				put("days-until", daysUntil + " day" + (daysUntil == -1 ? "" : "s") + " ago");
			} else {
				put("days-until", "in " + daysUntil + " day" + (daysUntil == -1 ? "" : "s"));
			}
		}};
	}
}
