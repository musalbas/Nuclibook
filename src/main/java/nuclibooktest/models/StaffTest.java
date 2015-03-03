package nuclibooktest.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import nuclibook.models.Tracer;
import nuclibooktest.test_utils.TestSqlServerConnection;
import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class StaffTest {

    @Test
    public void setAndCheckPasswordTest() throws SQLException, CannotHashPasswordException {
        Staff staff = new Staff();
        staff.setPassword("foobar");
        assertTrue("Password check failed for set password 'foobar'", staff.checkPassword("foobar"));
    }

    @Test
    public void isInLastPasswordsTest() throws CannotHashPasswordException {
        Staff staff = new Staff();

        staff.setPassword("foobar");
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar"));

        staff.setPassword("foobar2");
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar2"));

        staff.setPassword("foobar3");
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar2"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar3"));

        staff.setPassword("foobar4");
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar2"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar3"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar4"));

        staff.setPassword("foobar5");
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar2"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar3"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar4"));
        assertTrue("isInLastPasswords() returned false when it should have returned true", staff.isInLastPasswords("foobar5"));
        assertFalse("isInLastPasswords() returned true when it should have returned false", staff.isInLastPasswords("foobar"));
    }

}