package nuclibook.server;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.constants.C;
import nuclibook.models.Camera;
import nuclibook.models.CameraType;
import nuclibook.models.Patient;
import nuclibook.models.Staff;

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
				((JdbcConnectionSource) connection).setUsername(C.MYSQL_USERNAME);
				((JdbcConnectionSource) connection).setPassword(C.MYSQL_PASSWORD);
				initDB(connection);
			} catch (Exception e) {
				e.printStackTrace(); // TODO deal with exception
			}
		}

		return connection;
	}

    public static void initDB(ConnectionSource connection) {
		try {
			TableUtils.createTableIfNotExists(connection, Staff.class);
            TableUtils.createTableIfNotExists(connection, Patient.class);
            TableUtils.createTableIfNotExists(connection, Camera.class);
            TableUtils.createTableIfNotExists(connection, CameraType.class);
		} catch (SQLException e) {
			e.printStackTrace(); // TODO deal with exception
		}
	}
}
