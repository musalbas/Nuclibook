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
        assertTrue("The tracer could not be found",
                tracer.getId() == 1);
        assertNull("A tracer found that was not inserted into the table",
                TracerUtils.getTracer(30));
    }

    @Test
    public void testGetAllTracers() throws Exception {
        assertTrue("The size of the retrieved list is not equal to the rows placed into the table",
                TracerUtils.getAllTracers(false).size() == 2);
        assertTrue("The size of the retrieved list is not equal to the number of enabled tracers placed into the table",
                TracerUtils.getAllTracers(true).size() == 1);
    }
}
