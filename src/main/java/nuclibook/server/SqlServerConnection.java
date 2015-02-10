package nuclibook.server;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.constants.C;
import nuclibook.models.Radiographer;
import nuclibook.models.RadiographerAvailability;
import nuclibook.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnection {

	/* singleton pattern */

	private SqlServerConnection() {
	}

	private static ConnectionSource connection = null;

	public static ConnectionSource acquireConnection() {
		if (connection == null) {
			try {
                connection = new JdbcConnectionSource(C.MYSQL_URI);
                ((JdbcConnectionSource)connection).setUsername(C.MYSQL_USERNAME);
                ((JdbcConnectionSource)connection).setPassword(C.MYSQL_PASSWORD);
			} catch (Exception e) {
				e.printStackTrace(); // TODO deal with exception
			}

			initDB(connection);
		}

		return connection;
	}

    public static void initDB(ConnectionSource connection) {
		try {
			TableUtils.createTableIfNotExists(connection, User.class);
			TableUtils.createTableIfNotExists(connection, Radiographer.class);
			TableUtils.createTableIfNotExists(connection, RadiographerAvailability.class);
		} catch (SQLException e) {
			e.printStackTrace(); // TODO deal with exception
		}
	}
}
