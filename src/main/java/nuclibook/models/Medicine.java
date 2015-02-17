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

    public Medicine() { //Empty constructor for ORMLite
    }

    public Medicine(String name, int orderTime) {
        this.name = name;
        this.orderTime = orderTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getId() {
        return id;
    }
}
