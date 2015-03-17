package nuclibooktest.test_utils;

import nuclibook.entity_utils.PatientQuestionUtils;
import nuclibook.entity_utils.StaffAvailabilityUtils;
import nuclibook.models.Patient;
import nuclibook.models.PatientQuestion;
import nuclibook.models.StaffAvailability;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class TestPatientQuestionUtils extends AbstractUtilTest{

    public TestPatientQuestionUtils(String datasetPath, Class<?> tableClass) {
        super(datasetPath, tableClass);
    }

    @Parameterized.Parameters
    public static Collection datasetPath() {
        return Arrays.asList(new Object[][]{
                {"/test_datasets/patient_question_data_set.xml", PatientQuestion.class}
        });

    }

    @Test
    public void testGetPatientQuestion(){
        PatientQuestion patientQuestion = PatientQuestionUtils.getPatientQuestion(1);
        assertTrue(patientQuestion.getId() == 1);
        assertNull(PatientQuestionUtils.getPatientQuestion(20));
    }

    @Test
    public void testGetAllPatientQuestions() throws Exception {
        assertTrue(PatientQuestionUtils.getAllPatientQuestions().size() == 2);
    }

    @Test
    public void testGetPatientQuestionsByTherapyId(){
        List<PatientQuestion> patientQuestions = PatientQuestionUtils.getPatientQuestionsByTherapyId(3);
        assertTrue(patientQuestions.size() == 2);
    }
}
