package nuclibooktest.test_utils;

import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.models.Patient;
import nuclibook.server.SqlServerConnection;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;


public class AbstractUtilTest {
    private static ConnectionSource connectionSource;
    private static H2Connection dbunitConnection;
    private String datasetPath;
    private Class<?> tableClass;

    public AbstractUtilTest(String datasetPath, Class<?> tableClass){
        setDatasetPath(datasetPath);
        setTableClass(tableClass);
    }

    protected IDataSet getDataSet(String name) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(name);
        //assertNotNull("file"+name+" not found in classpath", inputStream);
        Reader reader = new InputStreamReader(inputStream);
        XmlDataSet dataset = new XmlDataSet(reader);
        return dataset;
    }

    @BeforeClass
    public static void initTest() throws SQLException, DatabaseUnitException {
        //create in-memory database
        String databaseUrl = "jdbc:h2:mem:test";
        connectionSource = SqlServerConnection.acquireConnection(databaseUrl, "", "");

        //get internal connection from connectionSource
        JdbcDatabaseConnection jdbcDatabaseConnection = (JdbcDatabaseConnection)connectionSource.getReadOnlyConnection();
        dbunitConnection = new H2Connection(jdbcDatabaseConnection.getInternalConnection(),"");

    }
    @Before
    public void initTable() throws Exception {
        IDataSet setupDataSet = getDataSet(datasetPath);
        DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection,
                setupDataSet);
    }

    @After
    public void resetTable() throws SQLException {
        TableUtils.clearTable(connectionSource, tableClass);
    }

    @AfterClass
    public static void tearDown(){
        connectionSource = null;
        dbunitConnection = null;
    }

    public String getDatasetPath() {
        return datasetPath;
    }

    public void setDatasetPath(String datasetPath) {
        this.datasetPath = datasetPath;
    }

    public Class<?> getTableClass() {
        return tableClass;
    }

    public void setTableClass(Class<?> tableClass) {
        this.tableClass = tableClass;
    }
}
