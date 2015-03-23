package nuclibooktest.validation_tests;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.StaffUtils;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import org.junit.*;;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ValidationTest extends AbstractValidationTest{

    @BeforeClass
    public static void setUp() throws CannotHashPasswordException, IOException {
        //set password of TestUsername1
        Staff staff = StaffUtils.getStaff(1);
        staff.setPassword("testpassword");
        AbstractEntityUtils.updateEntity(Staff.class, staff);
        TestResponse loginpage = request("GET", "/login");

        //set csrf-token value
        csrf = loginpage.getTagValue();
        TestResponse loginResponse = request("POST", "/login?csrf-token=" + csrf + "&username=TestUsername1&password=testpassword");
    }
    @Test
    public void testCreateUpdateStaffAvailabilityValidation() throws SQLException {
        try{
            //a valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-availability&entity-id=0&staff-id=1&day-of-week=1&start-time=04%3A00&end-time=05%3A00");

            assertTrue(testResponse.status == 200);

            //invalid day-of-week
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-availability&entity-id=0&staff-id=1&day-of-week=monday&start-time=04%3A00&end-time=05%3A00");

            assertEquals("failed_validation", testResponse.body);

            //invalid start time
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-availability&entity-id=0&staff-id=1&day-of-week=1&start-time=four&end-time=five");

            assertEquals("failed_validation", testResponse.body);

            //start time and end time are the same
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-availability&entity-id=0&staff-id=1&day-of-week=1&start-time=64%3A00&end-time=64%3A00");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateStaffPasswordChangeValidation() throws SQLException {
        try{
            //incorrect current password provided
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-password-change&password_old=nuclibook&password=nuclibook&password_check=nuclibook");

            assertEquals("CUSTOM:Your current password was incorrect", testResponse.body);

            //password and password_check do not match
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-password-change&password_old=testpassword&password=nuclibook&password_check=nucdfflibook");

            assertEquals("failed_validation", testResponse.body);

            //password is less than 6 digits long
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-password-change&password_old=testpassword&password=&password_check=nucdfflibook");

            assertEquals("CUSTOM:Password must be at least 6 characters long.", testResponse.body);
            
        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateCameraValidation() throws Exception {
        try{
            insertDataset("/test_datasets/camera_type_data_set.xml");
            //valid response
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=camera&entity-id=0&camera-type-id=1&room-number=Castle");

            assertTrue(testResponse.status == 200);

            //incorrect room number value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=camera&entity-id=0&camera-type-id=1&room-number=Castle!");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateStaffAbsenceValidation() throws SQLException {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-absence&entity-id=0&staff-id=1&from-date=2015-01-10" +
                            "&from-time=03%3A00&to-date=2015-01-14&to-time=10%3A00");

            assertTrue(testResponse.status == 200);

            //invalid from-date and to-date values
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-absence&entity-id=0&staff-id=1&from-date=2015-a-10" +
                            "&from-time=03%3A00&to-date=2015-v-14&to-time=10%3A00");

            assertEquals("failed_validation", testResponse.body);

            //invalid from-date and to-date values
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-absence&entity-id=0&staff-id=1&from-date=2015-00001-10" +
                            "&from-time=03%3A00&to-date=2015-00001-14&to-time=10%3A00");

            assertEquals("failed_validation", testResponse.body);

            //invalid from-time and to-time values
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-absence&entity-id=0&staff-id=1&from-date=2015-01-10" +
                            "&from-time=93%3A00&to-date=2015-01-14&to-time=80%3A67");

            assertEquals("failed_validation", testResponse.body);

            //to-date is before from-date
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-absence&entity-id=0&staff-id=1&from-date=2015-01-10" +
                            "&from-time=03%3A00&to-date=2015-01-09&to-time=03%3A00");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdatePatientValidation() throws SQLException {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                        "/entity-update?csrf-token=" + csrf +"&entity-type=patient&entity-id=0&name=fsdagfsdgsdg&hospital-number=sdfgdsgdsgfds&nhs-number=fsdgsdgfsdg&sex=Male&date-of-birth=2004-09-14");

            assertTrue(testResponse.status == 200);

            //invalid name value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf +"&entity-type=patient&entity-id=0&name=978!!!!&hospital-number=sdfgdsgdsgfds&nhs-number=fsdgsdgfsdg&sex=Male&date-of-birth=2004-09-14");

            assertEquals("failed_validation", testResponse.body);

            //invalid hospital number value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf +"&entity-type=patient&entity-id=0&name=Test&hospital-number=!!!!+++&nhs-number=fsdgsdgfsdg&sex=Male&date-of-birth=2004-09-14");

            assertEquals("failed_validation", testResponse.body);

            //invalid nhs number value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf +"&entity-type=patient&entity-id=0&name=Test&hospital-number=hospital2&nhs-number=!!!!!+++&sex=Male&date-of-birth=2004-09-14");

            assertEquals("failed_validation", testResponse.body);

            //invalid date-of-birth value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf +"&entity-type=patient&entity-id=0&name=Test&hospital-number=hospital2&nhs-number=abc23=Male&date-of-birth=gfd!!+");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateTracerValidation() throws SQLException {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=tracer&entity-id=0&name=testTracer&order-time=23");

            assertTrue(testResponse.status == 200);

            //invalid name value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=tracer&entity-id=0&name=!!!!++++&order-time=23");

            assertEquals("failed_validation", testResponse.body);

            //invalid order-time value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=tracer&entity-id=0&name=testTrace&order-time=!!!!");

            assertEquals("failed_validation", testResponse.body);

            //invalid order-time value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=tracer&entity-id=0&name=testTrace&order-time=twenty");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateStaffValidation() throws SQLException {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff&entity-id=0&name=testuser&username=testuser&role-id=2&password=password&password_check=password");

            assertTrue(testResponse.status == 200);

            //invalid name value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff&entity-id=0&name=7623&username=testuser&role-id=2&password=password&password_check=password");

            assertEquals("failed_validation", testResponse.body);

            //invalid name value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff&entity-id=0&name=!!+++&username=testuser1&role-id=2&password=password&password_check=password");

            assertEquals("failed_validation", testResponse.body);

            //invalid username value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff&entity-id=0&name=testuser&username=!!!!++++&role-id=2&password=password&password_check=password");

            assertEquals("failed_validation", testResponse.body);

            //username has already been taken
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff&entity-id=0&name=testuser&username=testuser&role-id=2&password=password&password_check=password");

            assertEquals("CUSTOM:Username has been taken", testResponse.body );

            //password is less than 6 characters long
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff&entity-id=0&name=testuser&username=testuser1&role-id=2&password=1234&password_check=1234");

            assertEquals("CUSTOM:Password must be at least 6 characters long.", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateStaffRoleValidation() throws SQLException {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-role&entity-id=0&label=Doctor&permission-4=4" +
                            "&permission-26=26&permission-1=1&permission-3=3&permission-18=18&permission-19=19&permission-9=9" +
                            "&permission-11=11&permission-27=27");
            assertTrue(testResponse.status == 200);

            //invalid label value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-role&entity-id=0&label=Doctor!!!!!&permission-4=4" +
                            "&permission-26=26&permission-1=1&permission-3=3&permission-18=18&permission-19=19&permission-9=9" +
                            "&permission-11=11&permission-27=27");

            assertEquals("failed_validation", testResponse.body);

            //invalid label value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-role&entity-id=0&label=Doctorlabellabellabellabellabellabellabel" +
                            "&permission-4=4&permission-26=26&permission-1=1&permission-3=3&permission-18=18&permission-19=19&permission-9=9" +
                            "&permission-11=11&permission-27=27");

            assertEquals("failed_validation", testResponse.body);

            //no permissions sent
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=staff-role&entity-id=0&label=Doctor");

            assertEquals("CUSTOM:Please select at least one permission", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateCameraTypeValidation() throws Exception {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=camera-type&entity-id=0&label=TestType");

            assertTrue(testResponse.status == 200);

            //invalid label value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=camera-type&entity-id=0&label=TestType45!");

            assertEquals("failed_validation", testResponse.body);

            //invalid label value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=camera-type&entity-id=0&label=TestTypeTestTypeTestType" +
                            "TestTypeTestTypeTestTypeTestTypeTestTypeTestType");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateUpdateTherapyValidation() throws Exception {
        try{
            insertDataset("/test_datasets/tracer_data_set.xml");

            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=therapy&entity-id=0&name=Test+Therapy&booking-section-0a=busy" +
                            "&booking-section-0b=15&booking-section-1a=wait&booking-section-1b=10&tracer-required-id=1&tracer-dose=20mg" +
                            "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1");

            assertTrue(testResponse.status == 200);

            //invalid name value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=therapy&entity-id=0&name=Test+Therapy56&booking-section-0a=busy" +
                            "&booking-section-0b=15&booking-section-1a=wait&booking-section-1b=10&tracer-required-id=1&tracer-dose=20mg" +
                            "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1");

            assertEquals("failed_validation", testResponse.body);

            //invalid tracer dose value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=therapy&entity-id=0&name=Test+Therapy&booking-section-0a=busy" +
                            "&booking-section-0b=15&booking-section-1a=wait&booking-section-1b=10&tracer-required-id=1&tracer-dose=!!20mg" +
                            "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1");

            assertEquals("failed_validation", testResponse.body);

            //invalid booking section values
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=therapy&entity-id=0&name=Test+Therapy&booking-section-0a=notBusy" +
                            "&booking-section-0b=15&booking-section-1a=notWaiting&booking-section-1b=10&tracer-required-id=1&tracer-dose=20mg" +
                            "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1");

            assertEquals("failed_validation", testResponse.body);

            //invalid booking-section value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=therapy&entity-id=0&name=Test+Therapy&booking-section-0a=busy" +
                            "&booking-section-0b=abc&booking-section-1a=wait&booking-section-1b=abc&tracer-required-id=1&tracer-dose=20mg" +
                            "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1");

            assertEquals("failed_validation", testResponse.body);

            //invalid booking-section value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=therapy&entity-id=0&name=Test+Therapy&booking-section-0a=busy" +
                            "&booking-section-0b=15-30b&booking-section-1a=wait&booking-section-1b=10&tracer-required-id=1&tracer-dose=20mg" +
                            "&camera-type-1=1&camera-type-2=2&patient-question-0=Test+Question1");


            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testCreateUpdateGenericEventValidation() throws Exception {
        try{
            //valid request
            TestResponse testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=generic-event&entity-id=0&title=Mission+Briefing" +
                            "&description=In+the+home+cinema+room.&from-date=2015-03-18&from-time=10%3A30&to-date=2015-03-18&" +
                            "to-time=11%3A30");

            assertTrue(testResponse.status == 200);

            //title valid less than 1 character long
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=generic-event&entity-id=0&title=" +
                            "&description=In+the+home+cinema+room.&from-date=2015-03-18&from-time=10%3A30&to-date=2015-03-18&" +
                            "to-time=11%3A30");

            assertEquals("failed_validation", testResponse.body);

            //invalid title value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=generic-event&entity-id=0&title=Mission+Briefing!!++" +
                            "&description=In+the+home+cinema+room.&from-date=2015-03-18&from-time=10%3A30&to-date=2015-03-18&" +
                            "to-time=11%3A30");

            assertEquals("failed_validation", testResponse.body);

            //invalid description value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=generic-event&entity-id=0&title=Mission+Briefing" +
                            "&description=In+the+!!!!home+cinema+room.&from-date=2015-03-18&from-time=10%3A30&to-date=2015-03-18&" +
                            "to-time=11%3A30");

            assertEquals("failed_validation", testResponse.body);

            //invalid from-date value
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=generic-event&entity-id=0&title=Mission+Briefing" +
                            "&description=In+the+home+cinema+room.&from-date=Two-Three-18&from-time=10%3A30&to-date=2015-03-18&" +
                            "to-time=11%3A30");

            assertEquals("failed_validation", testResponse.body);

            //from-date is greater than to-date
            testResponse = request("POST",
                    "/entity-update?csrf-token=" + csrf + "&entity-type=generic-event&entity-id=0&title=Mission+Briefing" +
                            "&description=In+the+home+cinema+room.&from-date=2015-03-18&from-time=10%3A30&to-date=2015-03-11&" +
                            "to-time=11%3A30");

            assertEquals("failed_validation", testResponse.body);

        }catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
