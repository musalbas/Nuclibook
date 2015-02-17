package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractEntityUtil {

	public static <E> List<E> getAllEntites(Class dbClass) {
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				return entityDao.queryForAll();
			} catch (SQLException e) {
				// fail
			}
		}
		return null;
	}

	public static <E> E getEntityById(Class dbClass, int id) {
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				E entity = entityDao.queryForId(id);
				if (entity != null) return entity;
			} catch (SQLException e) {
				// fail
			}
		}
		return null;
	}

	public static <E> List<E> getEntityByField(Class dbClass, String field, Object value) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				// search for user
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				List<E> entities = entityDao.queryForEq(field, value);
				if (entities != null) {
					return entities;
				}
			} catch (SQLException e) {
				// fail
			}
		}
		return null;
	}

	public static <E> void createEntity(Class dbClass, E entity) {
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				entityDao.create(entity);
			} catch (SQLException e) {
				// fail
			}
		}
	}

	public static <E> void update(E entity, Class dbClass) {
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				entityDao.update(entity);
			} catch (SQLException e) {
				// fail
			}
		}
	}

	public static <E> void delete(E entity, Class dbClass) {
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				entityDao.delete(entity);
			} catch (SQLException e) {
				// fail
			}
		}
	}

	public static <E> void deleteByField(E entity, Class dbClass, String field, Object arg) {
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				DeleteBuilder<E, Integer> deleteBuilder = entityDao.deleteBuilder();
				deleteBuilder.where().eq(field, arg);
				deleteBuilder.delete();
			} catch (SQLException e) {
				// fail
			}
		}
	}
}
