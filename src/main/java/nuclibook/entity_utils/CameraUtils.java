package nuclibook.entity_utils;

import nuclibook.models.Camera;

import java.util.List;

public class CameraUtils extends AbstractEntityUtils {

	public static Camera getCamera(String id) {
		try {
			return getCamera(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Camera getCamera(int id) {
		return getEntityById(Camera.class, id);
	}

	public static List<Camera> getAllCameras() {
		return getAllCameras(false);
	}

	public static List<Camera> getAllCameras(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Camera.class, "enabled", "1");
		} else {
			return getAllEntities(Camera.class);
		}
	}
}
