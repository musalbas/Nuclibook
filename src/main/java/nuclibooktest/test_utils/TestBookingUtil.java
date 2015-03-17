package nuclibooktest.test_utils;

import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.StaffRoleUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.Booking;
import nuclibook.models.CameraType;
import nuclibook.models.Staff;
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
public class TestBookingUtil extends AbstractUtilTest{
    public TestBookingUtil(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/booking_data_set.xml", CameraType.class}
        });
    }

    @Test
    public void testGetBooking(){
        Booking booking = BookingUtils.getBooking(1);
        assertTrue(booking.getId() == 1);
        assertNull(BookingUtils.getBooking(20));
    }

    @Test
    public void testGetBookingsByPatientId(){
        List<Booking> bookings = BookingUtils.getBookingsByPatientId(1);
        assertTrue(bookings.size() == 2);
    }

    @Test
    public void testGetBookingsByStatus(){
        List<Booking> bookings = BookingUtils.getBookingsByStatus("TestStatus1");
        assertTrue(bookings.size() == 2);
    }

    @Test
    public void testGetBookingsByDateRange() throws Exception {
        insertDataset("/test_datasets/booking_sections_data_set.xml");
        List<Booking> bookings = BookingUtils.getBookingsByDateRange(new DateTime(1426584600000l),
                                    new DateTime(1426585500000l));
        assertTrue(bookings.size() == 1);
        assertTrue(bookings.get(0).getId() == 1);
    }
}
