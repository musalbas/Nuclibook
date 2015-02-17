package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;
import java.util.List;

public class SecurityUtils {

	/* singleton pattern */

	private static Staff loggedInAs = null;

	private SecurityUtils() {
		// prevent instantiation
	}

	public static Staff attemptLogin(String username, String password) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				// search for user
				// TODO: replace with search by field from utils, when ready
				Dao<Staff, Integer> userDao = DaoManager.createDao(conn, Staff.class);
				List<Staff> matchedStaff = userDao.queryForEq("username", username);
				if (matchedStaff != null && matchedStaff.size() == 1) {
					Staff staff = matchedStaff.get(0);
					if (staff != null) {
						// check their password
						if (staff.checkPassword(password)) {
							// correct login!
							loggedInAs = staff;
							return staff;
						}
					}
				}
			} catch (SQLException | CannotHashPasswordException e) {
				// fail
			}
		}
		return null;
	}

	public static boolean checkLoggedIn() {
		return loggedInAs != null;
	}

	public static void destroyLogin() {
		loggedInAs = null;
	}

	public static Staff getCurrentUser() {
		return loggedInAs;
	}

}
