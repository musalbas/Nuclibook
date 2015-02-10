package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.OneToMany;
import java.util.List;

@DatabaseTable(tableName = "radiographers")
public class Radiographer {

    @DatabaseField(generatedId =  true)
    private int id;

    @DatabaseField(foreign = true)
    private User user;

    @OneToMany(mappedBy = "radiographer")
    private List<RadiographerAvailability> radiographerAvailabilities;

    public User getUser() {
        return user;
    }

}
