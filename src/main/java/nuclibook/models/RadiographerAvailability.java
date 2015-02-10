package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@DatabaseTable(tableName = "radiographerAvailabilities")
public class RadiographerAvailability {

    @DatabaseField(generatedId = true)
    private int id;

    @ManyToOne
    private Radiographer radiographer;

    public Radiographer getRadiographer() {
        return radiographer;
    }

}
