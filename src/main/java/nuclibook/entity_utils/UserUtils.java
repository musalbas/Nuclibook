package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.User;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;

public class UserUtils {

	public static String getUserName(int userId) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				// search for user
				Dao<User, Integer> userDao = DaoManager.createDao(conn, User.class);
				User user = userDao.queryForId(userId);
				if (user != null) {
					return user.getName();
				}
			} catch (SQLException e) {
				// fail
			}
		}
		return null;
	}

}
