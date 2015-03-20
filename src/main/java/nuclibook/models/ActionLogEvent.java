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

	public ActionLogEvent() {
	}

	public Integer getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}
}
