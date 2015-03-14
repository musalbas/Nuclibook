package nuclibook.entity_utils;

import nuclibook.models.Camera;
import nuclibook.models.CameraType;
import nuclibook.models.Therapy;

import java.util.ArrayList;
import java.util.HashMap;
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

	public static List<Camera> getCamerasByCameraType(CameraType cameraType) {
		return getEntitiesByField(Camera.class, "camera_type_id", cameraType.getId());
	}

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
