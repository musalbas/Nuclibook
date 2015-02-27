package nuclibooktest.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.Tracer;
import nuclibooktest.test_utils.TestSqlServerConnection;
import org.junit.AfterClass;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;

public class SqlServerConnectionTest {

	@Test
	public void testAcquireConnection() throws SQLException {
		ConnectionSource connection = TestSqlServerConnection.acquireConnection();
		assertTrue("SQL connection was not created", connection instanceof ConnectionSource);

		Dao<Tracer, Integer> therapyDao = DaoManager.createDao(connection, (Class) Tracer.class);
		assertTrue("Did not create last table (tracers)", therapyDao.isTableExists());
	}

	@AfterClass
	public static void tearDownClass() {
		TestSqlServerConnection.dropAllTables();
	}

}
