package nuclibook.entity_utils;

import nuclibook.models.PatientQuestions;
import java.util.List;

public class PatientQuestionsUtils extends AbstractEntityUtils {

	public static PatientQuestions getPatientQuestions(String id) {
		try {
			return getPatientQuestions(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static PatientQuestions getPatientQuestions(int id) {
		return getEntityById(PatientQuestions.class, id);
	}

	public static List<PatientQuestions> getAllPatientQuestions() {
		return getAllEntities(PatientQuestions.class);
	}

    public static List<PatientQuestions> getPatientQuestionsByTherapyId(String staffId) {
        try {
            return getPatientQuestionsByTherapyId(Integer.parseInt(staffId));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static List<PatientQuestions> getPatientQuestionsByTherapyId(int therapyId) {
        return getEntitiesByField(PatientQuestions.class, "therapy_id", therapyId);
    }

}
