package nuclibooktest.test_utils;


import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.CameraUtils;
import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.Camera;
import nuclibook.models.CameraType;
import nuclibook.models.StaffAvailability;
import nuclibook.models.Therapy;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestCameraUtils extends AbstractUtilTest{
    public TestCameraUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/camera_data_set.xml", Camera.class}
        });
    }

    @Test
    public void testGetCameraType(){
        Camera camera = CameraUtils.getCamera(1);
        assertTrue("Camera which exists which was not found", camera.getId() == 1);
        assertNull("Camera which does not exist found", CameraUtils.getCamera(20));
    }

    @Test
    public void testGeAllCameras() throws Exception {
        assertTrue("Size of retrieved list is not equal to number of rows in table",
                CameraUtils.getAllCameras(false).size() == 2);
        assertTrue("Size of retrieved list is not equal to number of enabled cameras in table",
                CameraUtils.getAllCameras(true).size() == 1);
    }

    @Test
    public void testGetCamerasForTherapy() throws Exception {
        insertDataset("/test_datasets/therapy_camera_data_set.xml");

        insertDataset("/test_datasets/therapies_data_set.xml");

        insertDataset("/test_datasets/camera_type_data_set.xml");

        Therapy therapy = TherapyUtils.getTherapy(1);
        List<Camera> cameras = CameraUtils.getCamerasForTherapy(therapy);

        assertTrue("Size of retrieved list is not equal to number of cameras with a therapy id of 1" ,
                cameras.size() == 1);
    }
}
