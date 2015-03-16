package nuclibooktest.test_utils;

import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.models.Patient;
import nuclibook.server.SqlServerConnection;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class TestPatientUtils extends AbstractUtilTest{


    public TestPatientUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }


    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/patient_data_set.xml", Patient.class}
        });
    }
    @Test
    public void testGetAllPatients() throws Exception {
        assertTrue(PatientUtils.getAllPatients(false).size() == 2);
        assertTrue(PatientUtils.getAllPatients(true).size() == 1);
    }

    @Test
    public void testGetPatient() throws Exception {
        Patient retrievedPatient = PatientUtils.getPatient(2);
        assertTrue(retrievedPatient.getId() == 2);
        assertNull(PatientUtils.getPatient(30));
    }


}
