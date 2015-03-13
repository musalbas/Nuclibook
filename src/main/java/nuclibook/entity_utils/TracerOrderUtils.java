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

public class TracerOrderUtils extends AbstractEntityUtils {

	public static TracerOrder getTracerOrder(String id) {
		try {
			return getTracerOrder(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static TracerOrder getTracerOrder(int id) {
		return getEntityById(TracerOrder.class, id);
	}

	public static List<TracerOrder> getAllTracerOrders() {
		return getAllTracerOrders(false);
	}

	public static List<TracerOrder> getAllTracerOrders(boolean pendingOnly) {
		if (pendingOnly) {
			return getTracerOrdersByStatus("pending");
		} else {
			return getAllEntities(TracerOrder.class);
		}
	}

	public static List<TracerOrder> getTracerOrdersByStatus(String status) {
		return getEntitiesByField(TracerOrder.class, "status", status);
	}

	public static List<TracerOrder> getTracerOrdersRequiredByDay(DateTime date) {
		return getTracerOrdersRequiredByDay(date, false);
	}

	public static List<TracerOrder> getTracerOrdersRequiredByDay(DateTime date, boolean pendingOnly) {
		// find all tracer orders required between the supplied dates
		Dao<TracerOrder, Integer> dao = acquireDao(TracerOrder.class);
		QueryBuilder<TracerOrder, Integer> query = dao.queryBuilder();
		try {
			// start and end must be inside the period
			Where<TracerOrder, Integer> where = query.where();
			if (pendingOnly) {
				where.and(
						where.between("order_by",
								date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).getMillis(),
								date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis()
						),
						where.eq("status", "pending")
				);
			} else {
				where.between("order_by",
						date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).getMillis(),
						date.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis()
				);
			}
			PreparedQuery<TracerOrder> preparedQuery = query.prepare();
			return dao.query(preparedQuery);
		} catch (SQLException | NullPointerException e) {
			return new ArrayList<>();
		}
	}

}
