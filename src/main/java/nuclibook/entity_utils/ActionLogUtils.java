package nuclibook.entity_utils;

import nuclibook.models.ActionLog;

import java.util.List;

public class ActionLogUtils extends AbstractEntityUtils {

	public static List<ActionLog> getAllActions() {
        return getAllEntities(ActionLog.class);
	}
}
