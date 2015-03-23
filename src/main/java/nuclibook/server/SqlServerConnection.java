package nuclibook.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.constants.C;
import nuclibook.models.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * This singleton class initialises and manages the SQL connection
 */
public class SqlServerConnection {

	/**
	 * Private constructor to enforce the singleton pattern
	 */
	private SqlServerConnection() {
	}

	private static ConnectionSource connection = null;

	/**
	 * Acquires the single instance of the SQL connection.
	 * @return The SQL connection source
	 */
	public static ConnectionSource acquireConnection() {
		return acquireConnection(C.MYSQL_URI, C.MYSQL_USERNAME, C.MYSQL_PASSWORD);
	}

	/**
	 * Acquires or creates the single instance of the SQL connection
	 * @param uri The DB URI
	 * @param username The DB username
	 * @param password The DB password
	 * @return The SQL connection source
	 */
	public static ConnectionSource acquireConnection(String uri, String username, String password) {
		if (connection == null) {
			try {
				connection = new JdbcConnectionSource(uri);
				((JdbcConnectionSource) connection).setUsername(username);
				((JdbcConnectionSource) connection).setPassword(password);
				initDB(connection);
			} catch (Exception e) {
				e.printStackTrace();
				LocalServer.fatalError("a connection could not be made to the database");
			}
		}

		return connection;
	}

	/**
	 * Creates all tables (if needed)
	 * @param connection The connection source, linked to the DB to be used.
	 */
	public static void initDB(ConnectionSource connection) throws SQLException, IOException {
		/*Dao<ActionLog, Integer> actionLogDao = DaoManager.createDao(connection, ActionLog.class);
		if (actionLogDao.isTableExists()) {
			return;
		}*/

		try {
			TableUtils.createTableIfNotExists(connection, ActionLog.class);
			TableUtils.createTableIfNotExists(connection, ActionLogEvent.class);
			TableUtils.createTableIfNotExists(connection, Booking.class);
			TableUtils.createTableIfNotExists(connection, BookingPatternSection.class);
			TableUtils.createTableIfNotExists(connection, BookingSection.class);
			TableUtils.createTableIfNotExists(connection, BookingStaff.class);
			TableUtils.createTableIfNotExists(connection, Camera.class);
			TableUtils.createTableIfNotExists(connection, CameraType.class);
			TableUtils.createTableIfNotExists(connection, GenericEvent.class);
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
			TableUtils.createTableIfNotExists(connection, Tracer.class);
			TableUtils.createTableIfNotExists(connection, TracerOrder.class);
		} catch (NullPointerException | SQLException e) {
			e.printStackTrace();
			LocalServer.fatalError("database tables could not be fully created");
		}

		// export default database
		URL queryURL = SqlServerConnection.class.getClass().getClassLoader().getResource("default_database.sql");
		String query = new String(Files.readAllBytes(Paths.get(queryURL.toString())));
		System.out.println(query);
	}
}
