package nuclibook.entity_utils;

import nuclibook.models.Therapy;

import java.util.List;

public class TherapyUtils extends AbstractEntityUtils {

	public static Therapy getTherapy(String id) {
		try {
			return getTherapy(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Therapy getTherapy(int id) {
		return getEntityById(Therapy.class, id);
	}

	public static List<Therapy> getAllTherapies() {
		return getAllTherapies(false);
	}

	public static List<Therapy> getAllTherapies(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Therapy.class, "enabled", "1");
		} else {
			return getAllEntities(Therapy.class);
		}
	}
}
