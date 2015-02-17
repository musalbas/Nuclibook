package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.Staff;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;

public class StaffUtils {

	// TODO: getStaff(int id);
	// TODO: getAllStaff();

	public static String getStaffName(int userId) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				// search for user
				Dao<Staff, Integer> staffDao = DaoManager.createDao(conn, Staff.class);
				Staff staff = staffDao.queryForId(userId);
				if (staff != null) {
					return staff.getName();
				}
			} catch (SQLException e) {
				// fail
			}
		}
		return null;
	}

}
