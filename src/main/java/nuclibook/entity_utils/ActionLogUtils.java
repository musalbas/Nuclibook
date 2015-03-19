package nuclibook.entity_utils;

import nuclibook.models.ActionLog;

import java.util.List;

public class ActionLogUtils extends AbstractEntityUtils {

	public static ActionLog getActionLog(String id) {
		try {
			return getActionLog(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static ActionLog getActionLog(int id) {
		return getEntityById(ActionLog.class, id);
	}

	public static List<ActionLog> getAllActions() {
        return getAllEntities(ActionLog.class);
	}
}
