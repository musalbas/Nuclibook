package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import nuclibook.models.GenericEvent;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for reading data from the generic_events table in the database.
 */
public class GenericEventUtils extends AbstractEntityUtils {

	/**
	 * Gets all the {@link nuclibook.models.GenericEvent} objects from the database.
	 *
	 * @return a list of all <code>GenericEvent</code> objects
	 */
	public static List<GenericEvent> getAllGenericEvents() {
		return getAllEntities(GenericEvent.class);
	}

	/**
	 * Gets the {@link nuclibook.models.GenericEvent} objects within the specified date range
	 *
	 * @param startDate the start date and time
	 * @param endDate   the end date and time
	 * @return a list of all <code>GenericEvent</code> objects within the date range
	 */
	public static List<GenericEvent> getGenericEventsByDateRange(DateTime startDate, DateTime endDate) {
		// find all generic events that have a start or end between the supplied dates
		Dao<GenericEvent, Integer> dao = acquireDao(GenericEvent.class);
		QueryBuilder<GenericEvent, Integer> query = dao.queryBuilder();
		List<GenericEvent> genericEvents;
		try {
			// either start or end inside today, or fully overlapping
			Where<GenericEvent, Integer> where = query.where();
			where.or(
					where.between("from", startDate.getMillis(), endDate.getMillis()),
					where.between("to", startDate.getMillis(), endDate.getMillis()),
					where.and(
							where.le("from", startDate.getMillis()),
							where.ge("to", endDate.getMillis())
					)
			);
			PreparedQuery<GenericEvent> preparedQuery = query.prepare();
			genericEvents = dao.query(preparedQuery);
		} catch (SQLException | NullPointerException e) {
			return new ArrayList<>();
		}

		return genericEvents;
	}
}
