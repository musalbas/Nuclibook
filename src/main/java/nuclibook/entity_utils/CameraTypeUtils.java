package nuclibook.entity_utils;

import nuclibook.models.CameraType;

import java.util.List;

public class CameraTypeUtils extends AbstractEntityUtils {

	public static CameraType getCameraType(String id) {
		try {
			return getCameraType(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static CameraType getCameraType(int id) {
		return getEntityById(CameraType.class, id);
	}

	public static List<CameraType> getAllCameraTypes() {
		return getAllCameraTypes(false);
	}

	public static List<CameraType> getAllCameraTypes(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(CameraType.class, "enabled", "1");
		} else {
			return getAllEntities(CameraType.class);
		}
	}
}
