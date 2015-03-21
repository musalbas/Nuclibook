package nuclibook.entity_utils;

import nuclibook.models.Therapy;

import java.util.List;

/**
 * A class for reading data from the therapies table in the database.
 */
public class TherapyUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.Therapy} object with the specified ID.
     *
     * @param id    the <code>Therapy</code> ID
     * @return  the associated <code>Therapy</code> object
     */
    public static Therapy getTherapy(String id) {
		try {
			return getTherapy(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

    /**
     * Gets the {@link nuclibook.models.Therapy} object with the specified ID.
     *
     * @param id    the <code>Therapy</code> ID
     * @return  the associated <code>Therapy</code> object
     */
	public static Therapy getTherapy(int id) {
		return getEntityById(Therapy.class, id);
	}

    /**
     * Gets all the {@link nuclibook.models.Therapy} objects in the database.
     *
     * Can return data only for the <code>enabled</code> fields.
     *
     * @param enabledOnly  specifies whether the method should get only <code>enabled Therapies</code>
     * @return  a list of <code>Therapy</code> objects
     */
	public static List<Therapy> getAllTherapies(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Therapy.class, "enabled", "1");
		} else {
			return getAllEntities(Therapy.class);
		}
	}
}
