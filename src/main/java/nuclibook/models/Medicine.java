package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "medicines")
public class Medicine {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 64)
    private String name;

    @DatabaseField(columnName = "order_time")
    private int orderTime;
}
