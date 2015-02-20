package nuclibook.entity_utils;

import nuclibook.models.Medicine;

import java.util.List;

public class MedicineUtils extends AbstractEntityUtils {

	public static Medicine getMedicine(String id) {
		try {
			return getMedicine(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Medicine getMedicine(int id) {
		return getEntityById(Medicine.class, id);
	}

	public static List<Medicine> getAllMedicines() {
		return getAllMedicines(false);
	}

	public static List<Medicine> getAllMedicines(boolean enabledOnly) {
		if (enabledOnly) {
			return getEntitiesByField(Medicine.class, "enabled", "1");
		} else {
			return getAllEntities(Medicine.class);
		}
	}
}
