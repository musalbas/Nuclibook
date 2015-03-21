package nuclibook.entity_utils;

import nuclibook.models.StaffAvailability;

import java.util.List;

/**
 * A class for reading data from the staff_availabilities table in the database.
 */
public class StaffAvailabilityUtils extends AbstractEntityUtils {

    /**
     * Gets the {@link nuclibook.models.StaffAvailability} object with the specified ID.
     * @param id    the <code>StaffAvailability</code> ID
     * @return  the associated <code>StaffAvailability</code> object
     */
    public static StaffAvailability getAvailabilityById(int id) {
        return getEntityById(StaffAvailability.class, id);
    }

    /**
     * Gets all the {@link nuclibook.models.StaffAvailability} objects for the specified {@link nuclibook.models.Staff} ID.
     *
     * @param staffId   the <code>Staff</code> ID
     * @return  the list of <code>StaffAvailability</code> objects for the specified <code>Staff</code> ID.
     */
    public static List<StaffAvailability> getAvailabilitiesByStaffId(int staffId) {
        return getEntitiesByField(StaffAvailability.class, "staff_id", staffId);
    }

    /**
     * Gets all the {@link nuclibook.models.StaffAvailability} objects in the database.
     *
     * @return  a list of <code>StaffAvailability</code> objects
     */
    public static List<StaffAvailability> getAllStaffAvailabilities() {
        return getAllEntities(StaffAvailability.class);
    }
}
