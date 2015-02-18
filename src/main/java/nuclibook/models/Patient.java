package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

@DatabaseTable(tableName = "patients")
public class Patient {

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
}
