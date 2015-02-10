package nuclibook.server;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.constants.C;

import java.sql.Connection;
import java.sql.DriverManager;

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
				e.printStackTrace();
			}
		}
		return connection;
	}

}
