package nuclibooktest.server;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.Staff;
import nuclibook.models.Therapy;
import nuclibooktest.test_utils.TestSqlServerConnection;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;

public class SqlServerConnectionTest {

    @Test
    public void testAcquireConnection() throws SQLException {
        ConnectionSource connection = TestSqlServerConnection.acquireConnection();
        assertTrue("SQL connection was not created", connection instanceof ConnectionSource);

        Dao<Therapy, Integer> therapyDao = DaoManager.createDao(connection, (Class) Therapy.class);
        assertTrue("Did not create last table (therapies)", therapyDao.isTableExists());
    }

    @AfterClass
    public static void tearDownClass() {
        TestSqlServerConnection.dropAllTables();
    }

}