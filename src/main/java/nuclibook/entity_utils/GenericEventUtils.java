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

public class GenericEventUtils extends AbstractEntityUtils {

	public static GenericEvent getGenericEvent(String id) {
		try {
			return getGenericEvent(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static GenericEvent getGenericEvent(int id) {
		return getEntityById(GenericEvent.class, id);
	}

	public static List<GenericEvent> getAllGenericEvents() {
		return getAllEntities(GenericEvent.class);
	}

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
