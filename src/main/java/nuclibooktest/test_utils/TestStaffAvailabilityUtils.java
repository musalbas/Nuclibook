package nuclibooktest.test_utils;

import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.models.StaffAvailability;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestStaffAvailabilityUtils extends AbstractUtilTest{
    public TestStaffAvailabilityUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/staff_availabilities_data_set.xml", StaffAvailability.class}
        });
    }

    @Test
    public void testGetStaffAvailability(){
        StaffAvailability staffAvailability = StaffAvailabilityUtils.getAvailabilityById(1);
        assertTrue("Staff availability which exists which was not found",
                staffAvailability.getId() == 1);
        assertNull("Staff availability which does not exist found", StaffAvailabilityUtils.getAvailabilityById(20));
    }

    @Test
    public void testGetAllStaffAvailabilities() throws Exception {
        assertTrue("Size of retrieved list is not equal to number of rows in table",
                StaffAvailabilityUtils.getAllStaffAvailabilities().size() == 2);
    }

    @Test
    public void testGetAvailabilitiesByStaffId(){
        List<StaffAvailability> staffAvailabilities = StaffAvailabilityUtils.getAvailabilitiesByStaffId(1);
        assertTrue("Size of retrieved list is not equal to number of availabilities of staff with id 1 in table",
                staffAvailabilities.size() == 2);
    }
}
