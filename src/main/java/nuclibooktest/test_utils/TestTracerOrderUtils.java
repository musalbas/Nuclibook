package nuclibooktest.test_utils;


import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.models.Tracer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class TestTracerOrderUtils extends AbstractUtilTest{


    public TestTracerOrderUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/tracer_order_data_set.xml", Tracer.class}
        });
    }

    //TODO
    @Test
    public void a(){

    }


}
