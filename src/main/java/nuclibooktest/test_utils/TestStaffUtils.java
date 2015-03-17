package nuclibooktest.test_utils;


import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Staff;
import nuclibook.models.StaffAvailability;
import nuclibook.models.StaffRole;
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
        assertTrue(staff.getId() == 1);
        assertNull(StaffRoleUtils.getStaffRole(20));
    }

    @Test
    public void testGetAllStaff() throws Exception {
        assertTrue(StaffUtils.getAllStaff(false).size() == 2);
        assertTrue(StaffUtils.getAllStaff(true).size() == 1);
    }

    @Test
    public void testGetStaffByUsername(){
        Staff staff = StaffUtils.getStaffByUsername("TestUsername1");
        assertTrue(staff.getId() == 1);
        assertNull(StaffUtils.getStaffByUsername("UsernameNotInDataset"));
    }

    @Test
    public void testUsernameExists(){
        assertTrue(StaffUtils.usernameExists("TestUsername1"));
        assertFalse(StaffUtils.usernameExists("UsernameNotInDataset"));
    }

    //todo isAvailable
    @Test
    public void testIsAvailable() throws Exception {
        insertDataset("/test_datasets/staff_availability_data_set.xml");
        insertDataset("/test_datasets/staff_absence_data_set.xml");


    }
}
