package nuclibooktest.test_utils;

import nuclibook.entity_utils.PermissionUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.models.Permission;
import nuclibook.models.StaffRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestPermissionUtils extends AbstractUtilTest{
    public TestPermissionUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/permissions_data_set.xml", Permission.class}
        });
    }

    @Test
    public void testGetPermission(){
        Permission permission = PermissionUtils.getPermission(1);
        assertTrue(permission.getId() == 1);
        assertNull(PermissionUtils.getPermission(20));
    }

    @Test
    public void testGetAllPermissions(){
        assertTrue(PermissionUtils.getAllPermissions().size() == 2);
    }
}
