package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model to represent an event in the action log.
 */
@DatabaseTable(tableName = "action_log_events")
public class ActionLogEvent {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String label;

    @DatabaseField(columnName = "is_error")
    private boolean isError;

    /**
     * Get the ID of the event.
     *
     * @return the ID of the event.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the label of the event.
     *
     * @return the label of the event.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label of the event.
     *
     * @param label The label of the event.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Check if it's an error or not.
     *
     * @return whether it's an error.
     */
    public boolean isError() {
        return isError;
    }

    /**
     * Set if it's an error or not.
     *
     * @param isError whether it's an error.
     */
    public void setError(boolean isError) {
        this.isError = isError;
    }
}
