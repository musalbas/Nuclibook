package nuclibooktest.validation_tests;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(value = Parameterized.class)
public class PermissionsTest extends AbstractValidationTest{
    //the url that will be passed to the permissions test
    String url;

    public PermissionsTest(String url) {
        this.url = url;
    }

    //parameters for the test
    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/entity-update?csrf-token=&entity-type=staff-availability&entity-id=0&staff-id=1&day-of-week=1&start-time=04%3A00&end-time=05%3A00"},
                {"/entity-update?csrf-token=&entity-type=staff-absence&entity-id=0&staff-id=1&from-date=2015-01-10" +
                        "&from-time=03%3A00&to-date=2015-01-14&to-time=10%3A00"},
                {"/entity-update?csrf-token=&entity-type=patient&entity-id=0&name=fsdagfsdgsdg&hospital-number=sdfgdsgdsgfds&nhs-number=fsdgsdgfsdg" +
                        "&sex=Male&date-of-birth=2004-09-14"},
                {"/entity-update?csrf-token=&entity-type=tracer&entity-id=0&name=testTracer&order-time=23"},
                {"/entity-update?csrf-token=&entity-type=staff&entity-id=0&name=testuser&username=testuser&role-id=2" +
                        "&password=password&password_check=password"},
                {"/entity-update?csrf-token=&entity-type=staff-role&entity-id=0&label=Doctor&permission-4=4" +
                        "&permission-26=26&permission-1=1&permission-3=3&permission-18=18&permission-19=19&permission-9=9" +
                        "&permission-11=11&permission-27=27"},
                {"/entity-update?csrf-token=&entity-type=camera-type&entity-id=0&label=TestType"},
                {"/entity-update?csrf-token=&entity-type=therapy&entity-id=0&name=Test+Therapy&booking-section-0a=busy" +
                        "&booking-section-0b=15&booking-section-1a=wait&booking-section-1b=10&tracer-required-id=1&tracer-dose=20mg" +
                        "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1"},
                {"/entity-update?csrf-token=&entity-type=generic-event&entity-id=0&title=Mission+Briefing" +
                        "&description=In+the+home+cinema+room.&from-date=2015-03-18&from-time=10%3A30&to-date=2015-03-18&" +
                        "to-time=11%3A30"},
                {"/entity-update?csrf-token=&entity-type=camera&entity-id=0&camera-type-id=1&room-number=Castle"}
        });
    }

    @BeforeClass
    public static void login() throws CannotHashPasswordException, IOException {

        //set password of TestUsername2
        Staff staff = StaffUtils.getStaff(2);
        staff.setPassword("testpassword");
        AbstractEntityUtils.updateEntity(Staff.class, staff);

        TestResponse loginpage = request("GET", "/login");

        //set csrf-token value
        csrf = loginpage.getTagValue();
        TestResponse loginResponse = request("POST", "/login?csrf-token=" + csrf + "&username=TestUsername2&password=testpassword");
    }

    @Test
    public void testCheckPermission() {
        try {
            //add csrf-token to the url
            String urlWithCsrf = url.replaceFirst("=","=" + csrf);

            //send a request to the server
            TestResponse testResponse = request("POST", urlWithCsrf);
            assertEquals("no_permission", testResponse.body);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
