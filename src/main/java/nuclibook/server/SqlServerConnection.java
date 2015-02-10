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

	private static Connection connection = null;

	public static Connection acquireConnection() {
		if (connection == null) {
			try {
                ConnectionSource connectionSource = new JdbcConnectionSource(C.MYSQL_URI);
                ((JdbcConnectionSource)connectionSource).setUsername(C.MYSQL_USERNAME);
                ((JdbcConnectionSource)connectionSource).setPassword(C.MYSQL_PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

}
