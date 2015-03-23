package nuclibooktest.test_utils;

import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.models.Patient;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class TestAbstractEntityUtils extends AbstractUtilTest{

    public TestAbstractEntityUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/patient_data_set.xml", Patient.class},

        });
    }

    @Test
    public void testRead(){
        Patient retrievedPatient = AbstractEntityUtils.getEntityById(Patient.class, 1);
        assertTrue("Retrieved patient does not have the same id as the patient in the table with and id of 1",
                retrievedPatient.getId() == 1);
        assertEquals("Retrieved patient does not have the same name as the patient in the table with and id of 1",
                retrievedPatient.getName(), "TestPatient1");
        assertEquals("Retrieved patient does not have the same hospital number as the patient in the table with and id of 1",
                retrievedPatient.getHospitalNumber(), "544");
        assertEquals("Retrieved patient does not have the same date of birth number as the patient in the table with and id of 1",
                retrievedPatient.getDateOfBirth().toString("YYYY-MM-dd"), "1970-01-05");
        assertEquals("Retrieved patient does not have the same date of enabled value as the patient in the table with and id of 1",
                retrievedPatient.getEnabled(), false);
    }
    @Test
    public void testCreate() throws Exception {
        //create a patient object
        Patient testPatient = new Patient();
        testPatient.setName("TestPatient");
        testPatient.setHospitalNumber("54");
        testPatient.setDateOfBirth(new DateTime(1994, 2, 5, 0, 0));

        //store patient object
        AbstractEntityUtils.createEntity(Patient.class, testPatient);

        //read patient from db
        List<Patient> patientList = AbstractEntityUtils.getEntitiesByField(Patient.class,"name", "TestPatient");

        assertEquals("The names of testPatient and the retrieved Patient object do not match",
                testPatient.getName(), patientList.get(0).getName());
        assertEquals("The hospital numbers of testPatient and the retrieved Patient object do not match",
                testPatient.getHospitalNumber(), patientList.get(0).getHospitalNumber());
        assertEquals("The DOBs of testPatient and the retrieved Patient object do not match",
                testPatient.getDateOfBirth(), patientList.get(0).getDateOfBirth());
        assertTrue("Size of retrieved list is not equal to number of rows in table",
                AbstractEntityUtils.getAllEntities(Patient.class).size() == 3);

        //update the patient
        testPatient = patientList.get(0);
        testPatient.setName("newName");
        System.out.print(testPatient.getId());
        AbstractEntityUtils.updateEntity(Patient.class, testPatient);
        Patient retrievedPatient = AbstractEntityUtils.getEntityById(Patient.class, testPatient.getId());
        assertEquals("The ids of testPatient and the retrieved Patient object do not match",
                testPatient.getId(), retrievedPatient.getId());

        //delete the patient
        AbstractEntityUtils.deleteEntity(Patient.class, testPatient);
        assertNull("The entity has not been deleted from the database",
                AbstractEntityUtils.getEntityById(Patient.class, testPatient.getId()));
    }

    @Test
     public void testUpdate(){
        Patient retrievedPatient = AbstractEntityUtils.getEntityById(Patient.class, 1);
        retrievedPatient.setName("TestPatientRenamed");
        AbstractEntityUtils.updateEntity(Patient.class, retrievedPatient);
        retrievedPatient = AbstractEntityUtils.getEntityById(Patient.class, 1);
        assertEquals("The name has not been updated properly",retrievedPatient.getName(),"TestPatientRenamed");
    }

    @Test
    public void testDelete(){
        Patient retrievedPatient = AbstractEntityUtils.getEntityById(Patient.class, 1);
        AbstractEntityUtils.deleteEntityById(Patient.class,retrievedPatient.getId());
        assertNull("Entity has not been deleted properly", AbstractEntityUtils.getEntityById(Patient.class,1));
    }

}
