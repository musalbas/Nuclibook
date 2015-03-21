package nuclibook.models;

import java.util.HashMap;

/**
 * An interface that allows any exportable entity in the system to be implemented.
 */
public interface Exportable {

    /**
     * Gets a hashmap of exportable data.
     * @return a hashmap of exportable data.
     */
    HashMap<String, String> getExportableHashMap();

}
