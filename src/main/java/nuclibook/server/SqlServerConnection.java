package nuclibook.server;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.constants.C;
import nuclibook.models.*;

import java.sql.SQLException;

public class SqlServerConnection {

	/* singleton pattern */

	private SqlServerConnection() {
	}

	private static ConnectionSource connection = null;

	public static ConnectionSource acquireConnection() {
		return acquireConnection(C.MYSQL_URI, C.MYSQL_USERNAME, C.MYSQL_PASSWORD);
	}

	public static ConnectionSource acquireConnection(String uri, String username, String password) {
		if (connection == null) {
			try {
				connection = new JdbcConnectionSource(uri);
				((JdbcConnectionSource) connection).setUsername(username);
				((JdbcConnectionSource) connection).setPassword(password);
				initDB(connection);
			} catch (Exception e) {
				// TODO deal with exception
				e.printStackTrace();
			}
		}

		return connection;
	}

	public static void initDB(ConnectionSource connection) {
		try {
			TableUtils.createTableIfNotExists(connection, ActionLog.class);
			TableUtils.createTableIfNotExists(connection, Booking.class);
			TableUtils.createTableIfNotExists(connection, BookingPatternSection.class);
			TableUtils.createTableIfNotExists(connection, BookingStaff.class);
			TableUtils.createTableIfNotExists(connection, Camera.class);
			TableUtils.createTableIfNotExists(connection, CameraType.class);
			TableUtils.createTableIfNotExists(connection, Tracer.class);
			TableUtils.createTableIfNotExists(connection, Patient.class);
			TableUtils.createTableIfNotExists(connection, PatientQuestion.class);
			TableUtils.createTableIfNotExists(connection, Permission.class);
			TableUtils.createTableIfNotExists(connection, Staff.class);
			TableUtils.createTableIfNotExists(connection, StaffAbsence.class);
			TableUtils.createTableIfNotExists(connection, StaffAvailability.class);
			TableUtils.createTableIfNotExists(connection, StaffRole.class);
			TableUtils.createTableIfNotExists(connection, StaffRolePermission.class);
			TableUtils.createTableIfNotExists(connection, Therapy.class);
			TableUtils.createTableIfNotExists(connection, TherapyCameraType.class);
		} catch (SQLException e) {
			// TODO deal with exception
			e.printStackTrace();
		}
	}
}
