package nuclibooktest.validation_tests;

import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.constants.C;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.LocalServer;
import nuclibook.server.SqlServerConnection;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract class AbstractValidationTest {

    protected static ConnectionSource connectionSource;
    protected static H2Connection dbunitConnection;
    protected static String sessionID;
    protected static String csrf;

    @BeforeClass
    public static void beforeClass() throws Exception {
        //start the local server
        LocalServer.main(null);

        //create in-memory database
        C.MYSQL_URI = "jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS TEST\\;" +
                "SET SCHEMA TEST";
        C.MYSQL_PASSWORD = "";
        C.MYSQL_USERNAME= "";
        connectionSource = SqlServerConnection.acquireConnection();

        //get internal connection from connectionSource
        JdbcDatabaseConnection jdbcDatabaseConnection = (JdbcDatabaseConnection)connectionSource.getReadOnlyConnection();
        dbunitConnection = new H2Connection(jdbcDatabaseConnection.getInternalConnection(), "TEST");
        DatabaseConfig config = dbunitConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");

        //insert datasets required for testing
        insertDataset("/test_datasets/permissions_data_set.xml");
        insertDataset("/test_datasets/staff_data_set.xml");
        insertDataset("/test_datasets/staff_roles_data_set.xml");
        insertDataset("/test_datasets/staff_role_permissions_data_set.xml");
    }

    @AfterClass
    public static void afterClass() {
        //stop the server
        Spark.stop();
    }

    //method to open a connection and send a request to the server
    protected static TestResponse request(String method, String path) throws IOException {
        URL url = new URL("http://localhost:4567" + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        Map<String, List<String>> m = connection.getRequestProperties();

        if(sessionID != null){
            connection.addRequestProperty("Cookie", "JSESSIONID=" + sessionID);
        }
        connection.connect();

        String body = IOUtils.toString(connection.getInputStream());

        if(sessionID == null) {
            String cookie = connection.getHeaderField("Set-Cookie");
            sessionID = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        }

        return new TestResponse(connection.getResponseCode(), body , connection.getHeaderFields());
    }

    //method to read and insert xml dataset into in in-memory database
    protected static void insertDataset(String path) throws Exception {
        InputStream inputStream = ValidationTest.class.getResourceAsStream(path);
        Reader reader = new InputStreamReader(inputStream);
        XmlDataSet dataset = new XmlDataSet(reader);
        DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection,
                dataset);
    }
}
