package nuclibook.entity_utils;

import nuclibook.models.TracerOrder;

import java.util.List;

public class TracerOrderUtils extends AbstractEntityUtils {

	public static TracerOrder getTracerOrder(String id) {
		try {
			return getTracerOrder(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static TracerOrder getTracerOrder(int id) {
		return getEntityById(TracerOrder.class, id);
	}

	public static List<TracerOrder> getTracerOrdersByStatus(String status) {
		return getEntitiesByField(TracerOrder.class, "status", status);
	}

}
