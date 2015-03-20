package nuclibook.entity_utils;

import nuclibook.models.ActionLogEvent;

import java.util.List;

/**
 * A class for reading data from the action_log_events table in the database.
 */
public class ActionLogEventUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.ActionLogEvent} event with the specified ID.
     *
     * @param id    the <code>ActionLogEvent</code> ID
     * @return  the associated <code>ActionLogEvent</code> object
     */
	public static ActionLogEvent getActionLogEvent(int id) {
		return getEntityById(ActionLogEvent.class, id);
	}
}
