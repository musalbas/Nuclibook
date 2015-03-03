package nuclibook.entity_utils;

import nuclibook.models.PatientQuestion;
import java.util.List;

public class PatientQuestionUtils extends AbstractEntityUtils {

	public static PatientQuestion getPatientQuestion(String id) {
		try {
			return getPatientQuestion(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static PatientQuestion getPatientQuestion(int id) {
		return getEntityById(PatientQuestion.class, id);
	}

	public static List<PatientQuestion> getAllPatientQuestions() {
		return getAllEntities(PatientQuestion.class);
	}

    public static List<PatientQuestion> getPatientQuestionsByTherapyId(String staffId) {
        try {
            return getPatientQuestionsByTherapyId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<PatientQuestion> getPatientQuestionsByTherapyId(int therapyId) {
        return getEntitiesByField(PatientQuestion.class, "therapy_id", therapyId);
    }

}
