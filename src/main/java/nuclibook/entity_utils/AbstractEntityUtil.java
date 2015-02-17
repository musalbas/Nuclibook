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

    public static <E> List<E> getEntityByField(int id, Class dbClass, String field, Object value){
        // set up server connection
        ConnectionSource conn = SqlServerConnection.acquireConnection();
        if (conn != null) {
            try {
                // search for user
                Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
                List<E> entityList = entityDao.queryForEq(field, value);
                if (entityList != null) {
                    return entityList;
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
                List<E> entityList = entityDao.queryForAll();
                if (entityList != null) {
                    return entityList;
                }
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
}
