package nuclibooktest.test_utils;

import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.CameraType;
import nuclibook.models.PatientQuestion;
import nuclibook.models.Therapy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestCameraTypeUtils extends AbstractUtilTest{
    public TestCameraTypeUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/camera_type_data_set.xml", CameraType.class}
        });
    }

    @Test
    public void testGetCameraType(){
        CameraType cameraType = CameraTypeUtils.getCameraType(1);
        assertTrue(cameraType.getId() == 1);
        assertNull(CameraTypeUtils.getCameraType(20));
    }

    @Test
    public void testGeAllCameraTypes() throws Exception {
        assertTrue(CameraTypeUtils.getAllCameraTypes(false).size() == 2);
        assertTrue(CameraTypeUtils.getAllCameraTypes(true).size() == 1);
    }
}
