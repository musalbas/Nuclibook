package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.User;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;

public class SecurityUtils {

	/* singleton pattern */

	private static User loggedInAs = null;

	private SecurityUtils() {
		// prevent instantiation
	}

	public static User attemptLogin(int userId, String password) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				// search for user
				Dao<User, Integer> userDao = DaoManager.createDao(conn, User.class);
				User user = userDao.queryForId(userId);
				if (user != null) {
					// check their password
					if (user.checkPassword(password)) {
						// correct login!
						loggedInAs = user;
						return user;
					}
				}
			} catch (SQLException e) {
				// fail
			} catch (CannotHashPasswordException e) {
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

	public static User getCurrentUser() {
		return loggedInAs;
	}

}
