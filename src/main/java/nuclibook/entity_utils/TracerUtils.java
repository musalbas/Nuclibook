package nuclibook.entity_utils;

import nuclibook.models.Tracer;

import java.util.List;

public class TracerUtils extends AbstractEntityUtils {

	public static Tracer getTracer(String id) {
		try {
			return getTracer(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Tracer getTracer(int id) {
		return getEntityById(Tracer.class, id);
	}

	public static List<Tracer> getAllTracers() {
		return getAllTracers(false);
	}

	public static List<Tracer> getAllTracers(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Tracer.class, "enabled", "1");
		} else {
			return getAllEntities(Tracer.class);
		}
	}
}
