package nuclibook.entity_utils;

import nuclibook.models.Exportable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportUtils {

    private static String rowAsCSV(List<String> row) {
        String output = "";

        for (String cell : row) {
            // TODO deal with cases where cell contains " as CSV would become invalid
            output += "\"" + cell + "\",";
        }

        output = output.substring(0, -1);

        return output;
    }

    public static <E> String exportCSV(Class dbClass) {
        List<E> allEntities = AbstractEntityUtils.getAllEntities(dbClass);

        String output = null;

        HashMap<String, String> entityHashMap;
        for (E entity : allEntities) {
            entityHashMap = ((Exportable) entity).getExportableHashMap();

            if (output == null) {
                List<String> header = new ArrayList<String>();
                for (Map.Entry<String, String> entityEntry : entityHashMap.entrySet()) {
                    String key = entityEntry.getKey();
                    header.add(key);
                    output += rowAsCSV(header);
                }
            }
        }

        return output;
    }

}