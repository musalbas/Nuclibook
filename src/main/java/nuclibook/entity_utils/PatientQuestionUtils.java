package nuclibook.entity_utils;

import nuclibook.models.PatientQuestion;
import java.util.List;

/**
 * A class for reading data from the patient_questions table in the database.
 */
public class PatientQuestionUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.PatientQuestion} object with the specified ID.
     * @param id
     * @return
     */
    public static PatientQuestion getPatientQuestion(int id) {
		return getEntityById(PatientQuestion.class, id);
	}

    /**
     * Gets all the {@link nuclibook.models.PatientQuestion} objects in the database.
     * @return   a list of all <code>PatientQuestion</code> objects
     */
	public static List<PatientQuestion> getAllPatientQuestions() {
		return getAllEntities(PatientQuestion.class);
	}

    /**
     * Gets all the {@link nuclibook.models.PatientQuestion} objects for the specified {@link nuclibook.models.Therapy} ID.
     * @param therapyId     the <code>Therapy</code> ID
     * @return  the associated list of <code>PatientQuestion</code> objects
     */
    public static List<PatientQuestion> getPatientQuestionsByTherapyId(int therapyId) {
        return getEntitiesByField(PatientQuestion.class, "therapy_id", therapyId);
    }

}
