package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.sql.Date;
import java.util.HashMap;

@DatabaseTable(tableName = "patients")
public class Patient implements Renderable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(width = 64)
	private String name;

	@DatabaseField
	private int hospitalNumber;

	@DatabaseField
	private Date dateOfBirth;

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

	public int getHospitalNumber() {
		return hospitalNumber;
	}

	public void setHospitalNumber(int hospitalNumber) {
		this.hospitalNumber = hospitalNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
			put("hospital-number", ((Integer) getHospitalNumber()).toString());
			put("date-of-birth", getDateOfBirth().toString());
		}};
	}
}
