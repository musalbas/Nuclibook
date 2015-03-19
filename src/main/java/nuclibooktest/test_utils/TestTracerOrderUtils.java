package nuclibooktest.test_utils;


import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.StaffAvailability;
import nuclibook.models.Tracer;
import nuclibook.models.TracerOrder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testGetTracer(){
        TracerOrder tracerOrder = TracerOrderUtils.getTracerOrder(1);
        assertTrue("The tracer order could not be found",
                tracerOrder.getId() == 1);
        assertNull("A tracer order found that was not inserted into the table",
                TracerOrderUtils.getTracerOrder(20));
    }

    @Test
    public void testGetAllTracerOrders() throws Exception {
        assertTrue("The size of the retrieved list is not equal to the number of rows placed into the table",
                TracerOrderUtils.getAllTracerOrders().size() == 2);
    }

    @Test
    public void testGetTracerOrdersByStatus(){
        List<TracerOrder> tracerOrders = TracerOrderUtils.getTracerOrdersByStatus("0");
        assertTrue("The size of the retrieved list is not equal to the number of tracer orders placed into the table with " +
                        "a status of 0",
                tracerOrders.size() == 2);
    }

    @Test
    public void testGetTracerOrdersByDate(){
        List<TracerOrder> tracerOrders = TracerOrderUtils.getTracerOrdersRequiredByDay(new DateTime(1426982500000l));
        assertTrue("The size of the retrieved list is not equal to the number of tracer orders placed into the table with " +
                        "a value in the date-required column which is less than or equal to the parameter passed to the method",
                tracerOrders.size() == 2);
    }

}
