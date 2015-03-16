package nuclibooktest.test_utils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.TracerUtils;
import nuclibook.models.Tracer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestTracerUtils extends AbstractUtilTest{

    public TestTracerUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/tracer_data_set.xml", Tracer.class}
        });
    }

    @Test
    public void testGetTracer(){
        Tracer tracer = TracerUtils.getTracer(1);
        assertTrue(tracer.getId() == 1);
        assertNull(TracerUtils.getTracer(30));
    }

    @Test
    public void testGetAllTracers() throws Exception {
        assertTrue(TracerUtils.getAllTracers(false).size() == 2);
        assertTrue(TracerUtils.getAllTracers(true).size() == 1);
    }
}
