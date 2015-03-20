package nuclibook.entity_utils;

import nuclibook.models.Tracer;

import java.util.List;

/**
 * A class for reading data from the tracers table in the database.
 */
public class TracerUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.Tracer} object with the specified ID.
     * @param id    the <code>Tracer</code> ID
     * @return  the associated <code>Tracer</code> object
     */
    public static Tracer getTracer(String id) {
		try {
			return getTracer(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

    /**
     * Gets the {@link nuclibook.models.Tracer} object with the specified ID.
     * @param id    the <code>Tracer</code> ID
     * @return  the associated <code>Tracer</code> object
     */
	public static Tracer getTracer(int id) {
		return getEntityById(Tracer.class, id);
	}

    /**
     * Gets all the {@link nuclibook.models.Tracer} objects in the database.
     *
     * @return  the list of all <code>Tracer</code> objects
     */
	public static List<Tracer> getAllTracers(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Tracer.class, "enabled", "1");
		} else {
			return getAllEntities(Tracer.class);
		}
	}
}
