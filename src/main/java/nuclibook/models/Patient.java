package nuclibook.models;

import com.j256.ormlite.field.DataType;
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
    private Date DOB;


    public Patient() { //Empty constructor for ORMLite
    }

    public Patient(String name, int hospitalNumber, Date DOB) {
        this.name = name;
        this.hospitalNumber = hospitalNumber;
        this.DOB = DOB;
    }

    public int getId() {
        return id;
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

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }
}
