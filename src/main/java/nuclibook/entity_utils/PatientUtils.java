package nuclibook.entity_utils;

import nuclibook.models.Patient;

import java.util.List;

public class PatientUtils extends AbstractEntityUtils {

	public static Patient getPatient(String id) {
		try {
			return getPatient(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Patient getPatient(int id) {
		return getEntityById(Patient.class, id);
	}

	public static List<Patient> getAllPatients() {
		return getAllPatients(false);
	}

	public static List<Patient> getAllPatients(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Patient.class, "enabled", "1");
		} else {
			return getAllEntities(Patient.class);
		}
	}
}
