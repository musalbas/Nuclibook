package nuclibooktest.test_utils;


import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffAvailability;
import nuclibook.models.StaffRole;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestStaffUtils extends AbstractUtilTest{
    public TestStaffUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/staff_data_set.xml", Staff.class}
        });
    }

    @Test
    public void testGetStaff(){
        Staff staff = StaffUtils.getStaff(1);
        assertTrue("Staff which exists which was not found", staff.getId() == 1);
        assertNull("Staff which does not exist found", StaffRoleUtils.getStaffRole(20));
    }

    @Test
    public void testGetAllStaff() throws Exception {
        assertTrue("Size of retrieved list is not equal to number of rows in table",
                StaffUtils.getAllStaff(false).size() == 2);
        assertTrue("Size of retrieved list is not equal to number of enabled staffs in table",
                StaffUtils.getAllStaff(true).size() == 2);
    }

    @Test
    public void testGetStaffByUsername(){
        Staff staff = StaffUtils.getStaffByUsername("TestUsername1");
        assertTrue("Username which exists was not found",staff.getId() == 1);
        assertNull("Username which exists was found", StaffUtils.getStaffByUsername("UsernameNotInDataset"));
    }

    @Test
    public void testUsernameExists(){
        assertTrue("Username which exists was not found",StaffUtils.usernameExists("TestUsername1"));
        assertFalse("Username which exists was found", StaffUtils.usernameExists("UsernameNotInDataset"));
    }
}
