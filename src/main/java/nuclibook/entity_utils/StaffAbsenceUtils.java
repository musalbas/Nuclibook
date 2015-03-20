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

/**
 * A class for reading data from the staff_absences table in the database.
 */
public class StaffAbsenceUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.StaffAbsence} object with the specified ID.
     * @param id    the <code>StaffAbsence</code> ID
     * @return  the associated <code>StaffAbsence</code> object
     */
    public static StaffAbsence getStaffAbsence(int id) {
		return getEntityById(StaffAbsence.class, id);
	}

    /**
     * Gets all the {@link nuclibook.models.StaffAbsence} objects for the specified {@link nuclibook.models.Staff} ID.
     * @param staffId   the <code>Staff</code> ID
     * @return  the list of <code>StaffAbsence</code> objects for the <code>Staff</code> ID
     */
	public static List<StaffAbsence> getStaffAbsencesByStaffId(int staffId) {
        return getEntitiesByField(StaffAbsence.class, "staff_id", staffId);
	}

    /**
     * Gets all the {@link nuclibook.models.StaffAbsence} objects in the database.
     *
     * @return  the list of all <code>StaffAbsence</code> objects
     */
	public static List<StaffAbsence> getAllStaffAbsences() {
		return getAllEntities(StaffAbsence.class);
	}

    /**
     * Gets all the {@link nuclibook.models.StaffAbsence} objects within the specified date range.
     *
     * @param startDate     the start date
     * @param endDate       the end date
     * @return  the list of <code>StaffAbsence</code> objects within the specified date range
     */
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
