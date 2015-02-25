package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;

import java.util.HashMap;

@DatabaseTable(tableName = "patients")
public class Patient implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String name;

	@DatabaseField(width = 64)
	private String hospitalNumber;

	@DatabaseField
	private String dateOfBirth;

	@DatabaseField(defaultValue = "true")
	private Boolean enabled;

	public Patient() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospitalNumber() {
		return hospitalNumber;
	}

	public void setHospitalNumber(String hospitalNumber) {
		this.hospitalNumber = hospitalNumber;
	}

	public DateTime getDateOfBirth() {
		return new DateTime(dateOfBirth);
	}

	public void setDateOfBirth(DateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth.toString();
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public HashMap<String, String> getHashMap() {
		return new HashMap<String, String>() {{
			put("id", getId().toString());
			put("name", getName());
			put("hospital-number", getHospitalNumber());
			put("date-of-birth", getDateOfBirth().toString("YYYY-MM-dd"));
		}};
	}
}
