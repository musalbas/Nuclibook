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
        assertTrue(staffAvailability.getId() == 1);
        assertNull(StaffAvailabilityUtils.getAvailabilityById(20));
    }

    @Test
    public void testGetAllStaffAvailabilities() throws Exception {
        assertTrue(StaffAvailabilityUtils.getAllStaffAvailabilities().size() == 2);
    }

    @Test
    public void testGetAvailabilitiesByStaffId(){
        List<StaffAvailability> staffAvailabilities = StaffAvailabilityUtils.getAvailabilitiesByStaffId(1);
        assertTrue(StaffAvailabilityUtils.getAllStaffAvailabilities().size() == 2);
    }
}
