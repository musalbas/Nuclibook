package nuclibooktest.validation_tests;

import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.constants.C;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.server.LocalServer;
import nuclibook.server.SqlServerConnection;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Session;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationTest {

    protected static ConnectionSource connectionSource;
    protected static H2Connection dbunitConnection;
    protected static String sessionID;

    @BeforeClass
    public static void beforeClass() throws Exception {
        LocalServer.main(null);
        //create in-memory database
        C.MYSQL_URI = "jdbc:h2:mem:test;INIT=CREATE SCHEMA IF NOT EXISTS TEST\\;" +
                "SET SCHEMA TEST";
        C.MYSQL_PASSWORD = "";
        C.MYSQL_USERNAME= "";

        connectionSource = SqlServerConnection.acquireConnection();

        //get internal connection from connectionSource
        JdbcDatabaseConnection jdbcDatabaseConnection = (JdbcDatabaseConnection)connectionSource.getReadOnlyConnection();
        //DatabaseMetaData metaData = jdbcDatabaseConnection.getInternalConnection().getMetaData();
        dbunitConnection = new H2Connection(jdbcDatabaseConnection.getInternalConnection(), "TEST");
        DatabaseConfig config = dbunitConnection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "`?`");
    }

    @Before
    public void setUp() throws Exception {
        insertDataset("/test_datasets/permissions_data_set.xml");
        insertDataset("/test_datasets/staff_data_set.xml");
        insertDataset("/test_datasets/staff_roles_data_set.xml");
        insertDataset("/test_datasets/staff_role_permissions_data_set.xml");
        Staff a = StaffUtils.getStaff(1);
        a.setPassword("testpassword");
        AbstractEntityUtils.updateEntity(Staff.class, a);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testCreateUpdateCameraWithPermissions() throws SQLException, IOException {

        TestResponse response = request("GET", "/login");
        String csrf = response.getTagValue();
        TestResponse loginResponse = request("POST", "/login?csrf-token=" + csrf + "&username=TestUsername1&password=testpassword");

        JdbcDatabaseConnection jdbcDatabaseConnection = (JdbcDatabaseConnection)connectionSource.getReadOnlyConnection();
        org.h2.tools.Server.startWebServer(jdbcDatabaseConnection.getInternalConnection());

        //TestResponse respone1 = response("POST", "/entity-update?");
    }

    private static TestResponse request(String method, String path) throws IOException {
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

        for(Map.Entry<String, List<String>> e : m.entrySet()) {
            System.out.println(e.getKey());
            for(String s: e.getValue()){
                System.out.println("\t" + s);
            }
        }

        String body = IOUtils.toString(connection.getInputStream());
        System.out.println(connection.getResponseCode());

        if(sessionID == null) {
            String cookie = connection.getHeaderField("Set-Cookie");
            sessionID = cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
        }
        //System.out.println(cookie);
        System.out.println(sessionID);

        return new TestResponse(connection.getResponseCode(), body , connection.getHeaderFields());
    }

    //method to read and insert xml dataset into in in-memory database
    protected void insertDataset(String path) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(path);
        Reader reader = new InputStreamReader(inputStream);
        XmlDataSet dataset = new XmlDataSet(reader);
        DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection,
                dataset);
    }
}
