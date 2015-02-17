package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.server.SqlServerConnection;
import java.sql.SQLException;
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

    public static <E> List<E> getByField(Class dbClass, String field, Object value) {
        // set up server connection
        ConnectionSource conn = SqlServerConnection.acquireConnection();
        if (conn != null) {
            try {
                // search for user
                Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
                List<E> entityName = entityDao.queryForEq(field, value);
                if (entityName != null) {
                    return entityName;
                }
            }catch(SQLException e) {
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
                return entityDao.queryForAll();
            }catch(SQLException e) {
                // fail
            }
        }
        return null;
    }

    public static <E> void createEntity(E entity, Class dbClass){
        ConnectionSource conn = SqlServerConnection.acquireConnection();
        if(conn != null){
            try{
                Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
                entityDao.create(entity);
            }catch(SQLException e){
                //fail
            }
        }
    }

    public static <E> void update(E entity, Class dbClass){
        ConnectionSource conn = SqlServerConnection.acquireConnection();
        if(conn != null){
            try{
                Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
                entityDao.update(entity);
            }catch(SQLException e){
                //fail
            }
        }

    }
}
