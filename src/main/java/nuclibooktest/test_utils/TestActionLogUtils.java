package nuclibooktest.test_utils;

import nuclibook.entity_utils.ActionLogUtils;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.models.ActionLog;
import nuclibook.models.Patient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestActionLogUtils extends AbstractUtilTest{
    public TestActionLogUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/action_log_data_set.xml", ActionLog.class},

        });
    }

    @Test
    public void testGetAllActions(){
        assertTrue(ActionLogUtils.getAllActions().size() == 2);
    }
}
