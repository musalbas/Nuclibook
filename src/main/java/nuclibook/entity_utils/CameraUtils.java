package nuclibook.entity_utils;

import nuclibook.models.Camera;
import nuclibook.models.CameraType;
import nuclibook.models.Therapy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class for reading data from the camera table in the database.
 */
public class CameraUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.Camera} object with the specified ID.
     *
     * @param id    the <code>Camera</code> ID
     * @return  the associated <code>Camera</code> object
     */
	public static Camera getCamera(String id) {
		try {
			return getCamera(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

    /**
     * Gets the {@link nuclibook.models.Camera} object with the specified ID.
     *
     * @param id    the <code>Camera</code> ID
     * @return  the associated <code>Camera</code> object
     */
	public static Camera getCamera(int id) {
		return getEntityById(Camera.class, id);
	}

    /**
     * Gets all the {@link nuclibook.models.Camera} objects in the database.
     *
     * Can return data only for the <code>enabled</code> fields.
     *
     * @param enabledOnly  specifies whether the method should get only <code>enabled</code> <code>Cameras</code>
     * @return  a list of <code>Camera</code> objects
     */
	public static List<Camera> getAllCameras(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Camera.class, "enabled", "1");
		} else {
			return getAllEntities(Camera.class);
		}
	}

    /**
     * Gets the {@link nuclibook.models.Camera} objects with the specified {@link nuclibook.models.CameraType}.
     * @param cameraType    the <code>CameraType</code>
     * @return  a list of the associated <code>Camera</code> objects
     */
	public static List<Camera> getCamerasByCameraType(CameraType cameraType) {
		return getEntitiesByField(Camera.class, "camera_type_id", cameraType.getId());
	}

    /**
     * Gets the {@link nuclibook.models.Camera} objects for the specified {@link nuclibook.models.Therapy}.
     * @param therapy    the <code>Therapy</code> to find <code>Cameras</code> for
     * @return  a list of the associated <code>Camera</code> objects
     */
	public static List<Camera> getCamerasForTherapy(Therapy therapy) {
		HashMap<Integer, Camera> allCameras = new HashMap<>();

		List<CameraType> allowableTypes = therapy.getCameraTypes();
		for (CameraType ct : allowableTypes) {
			List<Camera> cameras = getCamerasByCameraType(ct);
			if (cameras != null) {
				for (Camera c : cameras) {
					allCameras.put(c.getId(), c);
				}
			}
		}

		return new ArrayList<>(allCameras.values());
	}
}
