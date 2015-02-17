package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ainura on 17.02.2015.
 */
public abstract class AbstractEntityUtil {

    public static <E> E getEntityById(int id, Class dbClass) {
        // set up server connection
        ConnectionSource conn = SqlServerConnection.acquireConnection();
        if (conn != null) {
            try {
                // search for user
                Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
                E entityName = entityDao.queryForId(id);
                if (entityName != null) {
                    return entityName;
                }
            } catch (SQLException e) {
                // fail
            }
        }
        return null;
    }

    public static <E> List<E> geAllEntites(Class dbClass) {
        // set up server connection
        ConnectionSource conn = SqlServerConnection.acquireConnection();
        if (conn != null) {
            try {
                // search for user
                Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
                List<E> rawEntities = entityDao.queryForAll();
                if (rawEntities != null) {
                    List<E> outputList = new ArrayList<>();
                    for (Object tuple : rawEntities) {
                        outputList.add((E) tuple);
                    }
                    return outputList;
                }
            } catch (SQLException e) {
                // fail
            }
        }
        return null;
    }
}
