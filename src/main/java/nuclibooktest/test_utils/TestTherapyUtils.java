package nuclibooktest.test_utils;

import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.Therapy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestTherapyUtils extends AbstractUtilTest{

    public TestTherapyUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/therapies_data_set.xml", Therapy.class}
        });
    }

    @Test
    public void testGetTherapy(){
        Therapy therapy = TherapyUtils.getTherapy(1);
        assertTrue(therapy.getId() == 1);
        assertNull(TherapyUtils.getTherapy(30));
    }

    @Test
    public void testGetAllTherapies() throws Exception {
        assertTrue("The size of the retrieved list is not equal to the number of rows placed into the table",
                TherapyUtils.getAllTherapies(false).size() == 2);
        assertTrue("The size of the retrieved list is not equal to the number of enabled therapies placed into the table",
                TherapyUtils.getAllTherapies(true).size() == 1);
    }
}
