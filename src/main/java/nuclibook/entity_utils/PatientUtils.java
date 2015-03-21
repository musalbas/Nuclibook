package nuclibook.entity_utils;

import nuclibook.models.Patient;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

/**
 * A class for reading data from the patients table in the database.
 */
public class PatientUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.Patient} object with the specified ID.
     * @param id    the <code>Patient</code> ID.
     * @return  the associated <code>Patient</code> object
     */
    public static Patient getPatient(String id) {
		try {
			return getPatient(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

    /**
     * Gets the {@link nuclibook.models.Patient} object with the specified ID.
     * @param id    the <code>Patient</code> ID.
     * @return  the associated <code>Patient</code> object
     */
	public static Patient getPatient(int id) {
		return getEntityById(Patient.class, id);
	}

    /**
     * Gets all the {@link nuclibook.models.Patient} objects in the database.
     *
     * Can return data only for the <code>enabled</code> fields.
     *
     * @param enabledOnly  specifies whether the method should get only <code>enabled Patients</code>
     * @return  a list of <code>Patient</code> objects
     */
	public static List<Patient> getAllPatients(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Patient.class, "enabled", "1");
		} else {
			return getAllEntities(Patient.class);
		}
	}

    /**
     * Imports {@link nuclibook.models.Patient} records in the database from a <code>CSV/code> form.
     *
     * @param csvData   the <code>CSV</code> data to be imported
     * @return  the number of successful and failed imports
     * @throws IOException  when a <code>Patient's</code> sex is not in the appropriate format.
     */
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

				if (record.get(4).toLowerCase().equals("m") || record.get(4).toLowerCase().equals("male")) {
					sex = Patient.Sex.MALE;
				} else if (record.get(4).toLowerCase().equals("f") || record.get(4).toLowerCase().equals("female")) {
					sex = Patient.Sex.FEMALE;
				} else {
					throw new IOException("sex is not in appropriate format");
				}

				patient = new Patient();
				patient.setName(name);
				patient.setHospitalNumber(hospitalNumber);
				patient.setNhsNumber(nhsNumber);
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
