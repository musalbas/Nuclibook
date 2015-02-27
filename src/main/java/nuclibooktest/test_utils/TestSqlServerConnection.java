package nuclibooktest.test_utils;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.models.*;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;

public class TestSqlServerConnection {

	private static final String MYSQL_URI = "jdbc:mysql://bender.musalbas.com:3306/nuclibooktest";
	private static final String MYSQL_USERNAME = "nuclibooktest";
	private static final String MYSQL_PASSWORD = "qKRJ7Xj7MN2D3bV8";

	private TestSqlServerConnection() {
	}

	public static ConnectionSource acquireConnection() {
		return SqlServerConnection.acquireConnection(MYSQL_URI, MYSQL_USERNAME, MYSQL_PASSWORD);
	}

	public static void dropAllTables() {
		ConnectionSource connection = acquireConnection();

		try {
			TableUtils.dropTable(connection, ActionLog.class, true);
			TableUtils.dropTable(connection, Booking.class, true);
			TableUtils.dropTable(connection, BookingStaff.class, true);
			TableUtils.dropTable(connection, Camera.class, true);
			TableUtils.dropTable(connection, CameraType.class, true);
			TableUtils.dropTable(connection, Patient.class, true);
			TableUtils.dropTable(connection, Permission.class, true);
			TableUtils.dropTable(connection, Staff.class, true);
			TableUtils.dropTable(connection, StaffAbsence.class, true);
			TableUtils.dropTable(connection, StaffAvailability.class, true);
			TableUtils.dropTable(connection, StaffRole.class, true);
			TableUtils.dropTable(connection, StaffRolePermission.class, true);
			TableUtils.dropTable(connection, Therapy.class, true);
			TableUtils.dropTable(connection, Tracer.class, true);
		} catch (SQLException e) {
			// TODO deal with exception
			e.printStackTrace();
		}
	}

}
