package nuclibook.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
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
			TableUtils.createTableIfNotExists(connection, User.class);
            TableUtils.createTableIfNotExists(connection, Patient.class);
            TableUtils.createTableIfNotExists(connection, Camera.class);
            TableUtils.createTableIfNotExists(connection, CameraType.class);
            Dao<CameraType, Integer> cameraTypeDao = DaoManager.createDao(connection, CameraType.class);
            CameraType ct = new CameraType("camera A ");
            cameraTypeDao.create(ct);
            Dao<Camera, Integer> cameraDao = DaoManager.createDao(connection, Camera.class);
            cameraDao.create(new Camera(ct, "3"));
            System.out.println(cameraDao.countOf());
            Camera cam = cameraDao.queryForId(1);
            System.out.println(cam.getType().getLabel());

            cameraTypeDao.refresh(cam.getType());
            System.out.println(cam.getType().getLabel());

		} catch (SQLException e) {
			e.printStackTrace(); // TODO deal with exception
		}
	}
}
