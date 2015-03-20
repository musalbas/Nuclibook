package nuclibook.entity_utils;

import nuclibook.models.ActionLogEvent;

import java.util.List;

public class ActionLogEventUtils extends AbstractEntityUtils {

	public static ActionLogEvent getActionLogEvent(String id) {
		try {
			return getActionLogEvent(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static ActionLogEvent getActionLogEvent(int id) {
		return getEntityById(ActionLogEvent.class, id);
	}

	public static List<ActionLogEvent> getAllActions() {
		return getAllEntities(ActionLogEvent.class);
	}
}
