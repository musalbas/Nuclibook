package nuclibooktest.test_utils;

import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.models.Patient;
import nuclibook.server.SqlServerConnection;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        assertTrue(retrievedPatient.getId() == 1);
        assertEquals(retrievedPatient.getName(), "TestPatient1");
        assertEquals(retrievedPatient.getHospitalNumber(), "544");
        assertEquals(retrievedPatient.getDateOfBirth().toString("YYYY-MM-dd"), "1994-11-12");
        assertEquals(retrievedPatient.getEnabled(), false);
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
        assertTrue(AbstractEntityUtils.getAllEntities(Patient.class).size() == 3);

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
        assertEquals(retrievedPatient.getName(),"TestPatientRenamed");
    }

    @Test
    public void testDelete(){
        Patient retrievedPatient = AbstractEntityUtils.getEntityById(Patient.class, 1);
        AbstractEntityUtils.deleteEntityById(Patient.class,retrievedPatient.getId());
        assertNull(AbstractEntityUtils.getEntityById(Patient.class,1));
    }

}
