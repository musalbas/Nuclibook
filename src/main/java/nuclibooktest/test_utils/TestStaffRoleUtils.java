package nuclibooktest.test_utils;

import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.StaffRole;
import nuclibook.models.Therapy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestStaffRoleUtils extends AbstractUtilTest{
    public TestStaffRoleUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/staff_roles_data_set.xml", StaffRole.class}
        });
    }

    @Test
    public void getStaffRole(){
        StaffRole staffRole = StaffRoleUtils.getStaffRole(1);
        assertTrue("Staff role which exists which was not found", staffRole.getId() == 1);
        assertNull("Staff role which does not exist found", StaffRoleUtils.getStaffRole(20));
    }

    @Test
    public void testGetAllStaffRoles() throws Exception {
        assertTrue("Size of retrieved list is not equal to number of rows in table",
                StaffRoleUtils.getAllStaffRoles(false).size() == 2);
        assertTrue("Size of retrieved list is not equal to number of enabled staff roles in table",
                StaffRoleUtils.getAllStaffRoles(true).size() == 1);
    }

}
