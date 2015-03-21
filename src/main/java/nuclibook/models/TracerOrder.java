package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.HashMap;

/**
 * Model that represents a tracer order.
 */
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

    @DatabaseField(columnName = "date_required")
    private long dateRequired;

    @DatabaseField(columnName = "order_by")
    private long orderBy;

    @DatabaseField(width = 16)
    private String status;

    /**
     * Gets the ID of the tracer order.
     *
     * @return the ID of the tracer order.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the tracer being ordered.
     *
     * @return the tracer being ordered.
     * @see nuclibook.models.Tracer
     */
    public Tracer getTracer() {
        return tracer;
    }

    /**
     * Sets the tracer being ordered.
     *
     * @param tracer the tracer being ordered.
     * @see nuclibook.models.Tracer
     */
    public void setTracer(Tracer tracer) {
        this.tracer = tracer;
    }

    /**
     * Gets the dose of the tracer being ordered.
     *
     * @return the dose of the tracer being ordered.
     * @see nuclibook.models.Tracer
     */
    public String getTracerDose() {
        return tracerDose;
    }

    /**
     * Sets the dose of the tracer being ordered.
     *
     * @param tracerDose the dose of the tracer being ordered.
     * @see nuclibook.models.Tracer
     */
    public void setTracerDose(String tracerDose) {
        this.tracerDose = tracerDose;
    }

    /**
     * Gets the booking for which this order was made.
     *
     * @return the booking for which this order was made.
     * @see nuclibook.models.Booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Sets the booking for which this order was made.
     *
     * @param booking the booking for which this order was made.
     * @see nuclibook.models.Booking
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Gets the date when this tracer is required by.
     *
     * @return the date when this tracer is required by.
     */
    public DateTime getDateRequired() {
        return new DateTime(dateRequired);
    }

    /**
     * Sets the date when this tracer is required by.
     *
     * @param dateRequired the date when this tracer is required by.
     */
    public void setDateRequired(DateTime dateRequired) {
        this.dateRequired = dateRequired.getMillis();
    }

    /**
     * Gets the date which the tracer needs to be ordered by.
     *
     * @return the date which the tracer needs to be ordered by.
     */
    public DateTime getOrderBy() {
        return new DateTime(orderBy);
    }

    /**
     * Sets the date which the tracer needs to be ordered by.
     *
     * @param orderBy the date which the tracer needs to be ordered by.
     */
    public void setOrderBy(DateTime orderBy) {
        this.orderBy = orderBy.getMillis();
    }

    /**
     * Gets the status of the order.
     *
     * @return the status of the order.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status the status of the order.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("tracer-order-id", getId().toString());
            put("tracer-id", getTracer().getId().toString());
            put("tracer-name", getTracer().getName());
            put("tracer-dose", getTracerDose());
            put("date-required", getDateRequired().toString("YYYY-MM-dd HH:mm"));
            put("status", getStatus());

            // get status label
            String statusLabel = "default";
            if (getStatus().equals("pending")) statusLabel = "warning";
            if (getStatus().equals("ordered")) statusLabel = "success";
            put("status-with-label", "<span class=\"label label-as-badge label-" + statusLabel + "\">" + getStatus() + "</span>");

            // put days until string
            int daysUntil = Days.daysBetween(new LocalDate(), getOrderBy().toLocalDate()).getDays();
            if (daysUntil == 0) {
                put("days-until", "today");
            } else if (daysUntil < 0) {
                put("days-until", (daysUntil * -1) + " day" + (daysUntil == -1 ? "" : "s") + " ago");
            } else {
                put("days-until", "in " + daysUntil + " day" + (daysUntil == 1 ? "" : "s"));
            }
        }};
    }
}
