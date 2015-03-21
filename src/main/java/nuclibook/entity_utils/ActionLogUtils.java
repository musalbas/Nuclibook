package nuclibook.entity_utils;

import nuclibook.models.ActionLog;

import java.util.List;

/**
 * A class for reading data from the action_log table in the database.
 */
public class ActionLogUtils extends AbstractEntityUtils {

	/**
	 * Gets the {@link nuclibook.models.ActionLog} for the specified id.
	 *
	 * @param id the <code>ActionLog</code> id
	 * @return the associated <code>ActionLog</code> object
	 */
	public static ActionLog getActionLog(String id) {
		try {
			return getActionLog(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Gets the {@link nuclibook.models.ActionLog} for the specified id.
	 *
	 * @param id the <code>ActionLog</code> id
	 * @return the associated <code>ActionLog</code> object
	 */
	public static ActionLog getActionLog(int id) {
		return getEntityById(ActionLog.class, id);
	}

	/**
	 * Gets all the {@link nuclibook.models.ActionLog}s on the database.
	 *
	 * @return the list of all <code>ActionLog</code> objects
	 */
	public static List<ActionLog> getAllActions() {
		return getAllEntities(ActionLog.class);
	}
}
