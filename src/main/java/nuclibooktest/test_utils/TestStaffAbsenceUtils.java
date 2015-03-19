package nuclibooktest.test_utils;


import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import nuclibook.entity_utils.StaffAbsenceUtils;
import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.models.StaffAbsence;
import nuclibook.models.StaffAvailability;
import org.h2.tools.Server;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestStaffAbsenceUtils extends AbstractUtilTest{
    public TestStaffAbsenceUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/staff_absences_data_set.xml", StaffAbsence.class}
        });
    }

    @Test
    public void testGetStaffAvailability(){
        StaffAbsence staffAbsence = StaffAbsenceUtils.getStaffAbsence(1);
        assertTrue("Staff absence which exists which was not found",
                staffAbsence.getId() == 1);
        assertNull("Staff absence which does not exist found",
                StaffAbsenceUtils.getStaffAbsence(20));
    }

    @Test
    public void testGetAllStaffAbsences() throws Exception {
        assertTrue("Size of retrieved list is not equal to number of rows in table",
                StaffAbsenceUtils.getAllStaffAbsences().size() == 1);
    }

    @Test
    public void testGetStaffAbsencesByStaffId(){
        List<StaffAbsence> staffAbsences = StaffAbsenceUtils.getStaffAbsencesByStaffId(2);
        assertTrue("Size of retrieved list is not equal to number of staff absences with a staff id of 2",
                staffAbsences.size() == 1);
    }

    @Test
    public void testGetStaffAbsencesByDateRange() throws SQLException {
        DateTime from = new DateTime(53100l);
        DateTime to = new DateTime(53900l);
        List<StaffAbsence> staffAbsences = StaffAbsenceUtils.getStaffAbsencesByDateRange(from, to);
        assertTrue(staffAbsences.size() == 2);
    }
}
