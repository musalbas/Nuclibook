package nuclibook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nuclibook.server.Renderable;

import java.util.HashMap;

/**
 * Model to represent a tracer.
 */
@DatabaseTable(tableName = "tracers")
public class Tracer implements Renderable {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(width = 64)
    private String name;

    @DatabaseField(columnName = "order_time")
    private int orderTime;

    @DatabaseField(defaultValue = "true")
    private Boolean enabled;

	/**
	 * Blank constructor for ORM.
	 */
	public Tracer() {
	}

    /**
     * Gets the ID of the tracer.
     *
     * @return the ID of the tracer.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the tracer.
     *
     * @param id the ID of the tracer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the tracer.
     *
     * @return the name of the tracer.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tracer.
     *
     * @param name the name of the tracer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the lead order time of the tracer.
     *
     * @return the lead order time of the tracer.
     */
    public int getOrderTime() {
        return orderTime;
    }

    /**
     * Sets the lead order time of the tracer.
     *
     * @param orderTime the lead order time of the tracer.
     */
    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * Gets whether it's enabled.
     *
     * @return whether it's enabled.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets whether it's enabled.
     *
     * @param enabled whether it's enabled.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>() {{
            put("id", getId().toString());
            put("tracer-id", getId().toString());
            put("tracer-id-all", getId().toString());
            put("name", getName());
            put("tracer-name", getName());
            put("tracer-name-all", getName());
            put("order-time", ((Integer) getOrderTime()).toString());
        }};
    }
}
