package nuclibook.entity_utils;

import nuclibook.models.Patient;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;

import java.io.IOException;
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

	public static Integer[] importPatientsCSV(String csvData) throws IOException {
		CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT);

		Integer success = 0;
		Integer fail = 0;

		String name;
		String hospitalNumber;
		String nhsNumber;
		DateTime dateOfBirth;
		Patient.Sex sex;

		Patient patient;

		for (CSVRecord record : parser) {
			try {
				name = record.get(0);
				hospitalNumber = record.get(1);
				nhsNumber = record.get(2);
				dateOfBirth = new DateTime(record.get(3));

				if (record.get(4).toLowerCase() == "m" || record.get(4).toLowerCase() == "male") {
					sex = Patient.Sex.MALE;
				} else if (record.get(4).toLowerCase() == "f" || record.get(4).toLowerCase() == "female") {
					sex = Patient.Sex.FEMALE;
				} else {
					throw new IOException("sex is not in appropriate format");
				}

				patient = new Patient();
				patient.setName(name);
				patient.setHospitalNumber(hospitalNumber);
				patient.setName(nhsNumber);
				patient.setDateOfBirth(dateOfBirth);
				patient.setSex(sex);

				AbstractEntityUtils.createEntity(Patient.class, patient);

				success += 1;
			} catch (Exception e) {
				fail += 1;
			}
		}

		Integer[] ret = {success, fail};
		return ret;
	}

}
