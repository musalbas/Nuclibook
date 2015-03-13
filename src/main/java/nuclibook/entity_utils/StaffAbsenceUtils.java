package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import nuclibook.models.StaffAbsence;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffAbsenceUtils extends AbstractEntityUtils {

	public static StaffAbsence getStaffAbsence(String id) {
		try {
			return getStaffAbsence(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static StaffAbsence getStaffAbsence(int id) {
		return getEntityById(StaffAbsence.class, id);
	}

	public static List<StaffAbsence> getStaffAbsencesByStaffId(String staffId) {
		try {
			return getStaffAbsencesByStaffId(Integer.parseInt(staffId));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static List<StaffAbsence> getStaffAbsencesByStaffId(int staffId) {
		return getEntitiesByField(StaffAbsence.class, "staff_id", staffId);
	}

	public static List<StaffAbsence> getAllStaffAbsences() {
		return getAllEntities(StaffAbsence.class);
	}

	public static List<StaffAbsence> getStaffAbsencesByDateRange(DateTime startDate, DateTime endDate) {
		// find all staff absences that have a start or end between the supplied dates
		Dao<StaffAbsence, Integer> dao = acquireDao(StaffAbsence.class);
		QueryBuilder<StaffAbsence, Integer> query = dao.queryBuilder();
		List<StaffAbsence> staffAbsences;
		try {
			// either start or end inside today, or fully overlapping
			Where<StaffAbsence, Integer> where = query.where();
			where.or(
					where.between("from", startDate.getMillis(), endDate.getMillis()),
					where.between("to", startDate.getMillis(), endDate.getMillis()),
					where.and(
							where.le("from", startDate.getMillis()),
							where.ge("to", endDate.getMillis())
					)
			);
			PreparedQuery<StaffAbsence> preparedQuery = query.prepare();
			staffAbsences = dao.query(preparedQuery);
		} catch (SQLException | NullPointerException e) {
			return new ArrayList<>();
		}

		return staffAbsences;
	}
}
