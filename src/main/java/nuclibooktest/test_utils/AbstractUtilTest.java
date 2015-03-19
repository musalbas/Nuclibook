package nuclibooktest.test_utils;

import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.models.Patient;
import nuclibook.server.SqlServerConnection;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;


public class AbstractUtilTest {
    protected static ConnectionSource connectionSource;
    protected static H2Connection dbunitConnection;
    protected String datasetPath;
    private Class<?> tableClass;

    public AbstractUtilTest(String datasetPath, Class<?> tableClass){
        setDatasetPath(datasetPath);
        setTableClass(tableClass);
    }

    //method to read and insert xml dataset into in in-memory database
    protected void insertDataset(String path) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(path);
        Reader reader = new InputStreamReader(inputStream);
        XmlDataSet dataset = new XmlDataSet(reader);
        DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection,
                dataset);
    }

    //create in memory-database
    @BeforeClass
    public static void initTest() throws SQLException, DatabaseUnitException {
        //create in-memory database
        String databaseUrl = "jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS TEST\\;" +
                "SET SCHEMA TEST";;
        connectionSource = SqlServerConnection.acquireConnection(databaseUrl, "", "");

        //get internal connection from connectionSource
        JdbcDatabaseConnection jdbcDatabaseConnection = (JdbcDatabaseConnection)connectionSource.getReadOnlyConnection();
        //DatabaseMetaData metaData = jdbcDatabaseConnection.getInternalConnection().getMetaData();
        dbunitConnection = new H2Connection(jdbcDatabaseConnection.getInternalConnection(), "TEST");
        DatabaseConfig config = dbunitConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");

    }

    //insert dataset into the in-memory database before each test
    @Before
    public void initTable() throws Exception {
        insertDataset(datasetPath);
    }

    //clear out the table
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
