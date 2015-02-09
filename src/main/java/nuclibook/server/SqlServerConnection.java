package nuclibook.server;

import nuclibook.constants.C;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerConnection {

	/* singleton pattern */

	private SqlServerConnection() {

	}

	private static Connection connection = null;

	public static Connection acquireConnection() {
		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection(C.MYSQL_URI, C.MYSQL_USERNAME, C.MYSQL_PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

}
