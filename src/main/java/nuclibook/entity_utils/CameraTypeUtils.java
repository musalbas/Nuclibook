package nuclibook.entity_utils;

import nuclibook.models.CameraType;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for reading data from the camera_types table in the database.
 */
public class CameraTypeUtils extends AbstractEntityUtils {

	/**
	 * Gets the {@link nuclibook.models.CameraType} object with the specified ID.
	 *
	 * @param id the <code>CameraType</code> ID
	 * @return the associated <code>CameraType</code> object
	 */
	public static CameraType getCameraType(String id) {
		try {
			new ArrayList<String>();
			return getCameraType(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Gets the {@link nuclibook.models.CameraType} object for the specified ID.
	 *
	 * @param id the <code>CameraType</code> ID
	 * @return the associated <code>CameraType</code> object
	 */
	public static CameraType getCameraType(int id) {
		return getEntityById(CameraType.class, id);
	}

	/**
	 * Gets all the {@link nuclibook.models.CameraType} objects in the database.
	 * <p>
	 * Can return data only for the <code>enabled</code> fields.
	 *
	 * @param enabledOnly specifies whether the method should get only <code>enabled CameraTypes</code>
	 * @return a list of <code>CameraType</code> objects
	 */
	public static List<CameraType> getAllCameraTypes(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(CameraType.class, "enabled", "1");
		} else {
			return getAllEntities(CameraType.class);
		}
	}
}
