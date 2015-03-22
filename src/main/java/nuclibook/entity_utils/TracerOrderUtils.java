package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import nuclibook.models.TracerOrder;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for reading data from the tracer_orders table in the database.
 */
public class TracerOrderUtils extends AbstractEntityUtils {

	/**
	 * Gets the {@link nuclibook.models.TracerOrder} object with the specified ID.
	 *
	 * @param id the <code>TracerOrder</code> ID
	 * @return the associated <code>TracerOrder</code> object
	 */
	public static TracerOrder getTracerOrder(String id) {
		try {
			return getTracerOrder(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Gets the {@link nuclibook.models.TracerOrder} object with the specified ID.
	 *
	 * @param id the <code>TracerOrder</code> ID
	 * @return the associated <code>TracerOrder</code> object
	 */
	public static TracerOrder getTracerOrder(int id) {
		return getEntityById(TracerOrder.class, id);
	}

	/**
	 * Gets all the {@link nuclibook.models.TracerOrder} objects in the database.
	 *
	 * @return a list of all <code>TracerOrder</code> objects
	 */
	public static List<TracerOrder> getAllTracerOrders() {
		return getAllTracerOrders(false);
	}

	/**
	 * Gets all the {@link nuclibook.models.TracerOrder} objects in the database.
	 * <p>
	 * Can return data only for the <code>pending</code> fields.
	 *
	 * @param pendingOnly specifies whether the method should get only <code>pending TracerOrders</code>
	 * @return a list of <code>TracerOrders</code> objects
	 */
	public static List<TracerOrder> getAllTracerOrders(boolean pendingOnly) {
		if (pendingOnly) {
			return getTracerOrdersByStatus("pending");
		} else {
			return getAllEntities(TracerOrder.class);
		}
	}

	/**
	 * Gets all the {@link nuclibook.models.TracerOrder} objects with the specified status.
	 *
	 * @param status the <code>TracerOrder</code> status
	 * @return a list of the <code>TracerOrder</code> objects with the specified status
	 */
	public static List<TracerOrder> getTracerOrdersByStatus(String status) {
		return getEntitiesByField(TracerOrder.class, "status", status);
	}

	/**
	 * Gets all the {@link nuclibook.models.TracerOrder} objects on the specified date.
	 *
	 * @param date the date to look for
	 * @return a list of the <code>TracerOrder</code> objects on the specified date
	 */
	public static List<TracerOrder> getTracerOrdersRequiredByDay(DateTime date) {
		return getTracerOrdersRequiredByDay(date, false);
	}

	/**
	 * Gets all the {@link nuclibook.models.TracerOrder} objects with the specified date and status.
	 *
	 * @param date        the date to look for
	 * @param pendingOnly specifies whether the method should get only <code>pending TracerOrders</code>
	 * @return a list of all <code>TracerOrders</code> with the specified date and status
	 */
	public static List<TracerOrder> getTracerOrdersRequiredByDay(DateTime date, boolean pendingOnly) {
		// find all tracer orders required between the supplied dates
		Dao<TracerOrder, Integer> dao = acquireDao(TracerOrder.class);
		QueryBuilder<TracerOrder, Integer> query = dao.queryBuilder();
		try {
			// start and end must be inside the period
			Where<TracerOrder, Integer> where = query.where();
			if (pendingOnly) {
				where.and(
						where.ge("order_by", date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis()),
						where.le("order_by", date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).getMillis()),
						where.eq("status", "pending")
				);
			} else {
				where.and(
						where.ge("order_by", date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis()),
						where.le("order_by", date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).getMillis())
				);
			}
			PreparedQuery<TracerOrder> preparedQuery = query.prepare();
			return dao.query(preparedQuery);
		} catch (SQLException | NullPointerException e) {
			return new ArrayList<>();
		}
	}

}