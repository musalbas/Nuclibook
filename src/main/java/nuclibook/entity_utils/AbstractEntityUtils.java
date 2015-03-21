package nuclibook.entity_utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import nuclibook.server.SqlServerConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * A helper class for manipulating the database objects.
 * It provides code that can be reused.
 */
public abstract class AbstractEntityUtils {

	/**
	 * Queries the database for all rows in the database for the specified entity and returns it.
	 *
	 * @param dbClass the entity type
	 * @return a list of data for the entity
	 */
	public static <E> List<E> getAllEntities(Class dbClass) {
		// set up server connection
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

	/**
	 * Queries the database for the data of a specific entity ID and returns it.
	 *
	 * @param dbClass the entity type
	 * @param id      the entity ID
	 * @return a list of data for the entity ID
	 */
	public static <E> E getEntityById(Class dbClass, int id) {
		// set up server connection
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

	/**
	 * Queries the database for the data by a specific field value and returns it.
	 *
	 * @param dbClass the entity type
	 * @param field   the field name
	 * @param value   the specified value for the field
	 * @return a list of the data for the entity with the specified field value
	 */
	public static <E> List<E> getEntitiesByField(Class dbClass, String field, Object value) {
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

	/**
	 * Creates a new record in the database and immediately returns the entity that was just created.
	 *
	 * @param dbClass the entity type
	 * @param entity  the entity to be created
	 * @return the created entity
	 */
	public static <E> E createEntity(Class dbClass, E entity) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				entityDao.create(entity);
				return entity;
			} catch (SQLException e) {
				// fail
				return null;
			}
		}
		return null;
	}

	/**
	 * Updates the specified record in the database.
	 *
	 * @param dbClass the entity type
	 * @param entity  the entity to be updated
	 */
	public static <E> void updateEntity(Class dbClass, E entity) {
		// set up server connection
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

	/**
	 * Deletes the specified record in the database.
	 *
	 * @param dbClass the entity type
	 * @param entity  the entity to be deleted
	 */
	public static <E> void deleteEntity(Class dbClass, E entity) {
		// set up server connection
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

	/**
	 * Deletes a record in the database by the specified id.
	 *
	 * @param dbClass the  entity type
	 * @param id      the entity id
	 */
	public static <E> void deleteEntityById(Class dbClass, int id) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				Dao<E, Integer> entityDao = DaoManager.createDao(conn, dbClass);
				DeleteBuilder<E, Integer> deleteBuilder = entityDao.deleteBuilder();
				deleteBuilder.where().idEq(id);
				deleteBuilder.delete();
			} catch (SQLException e) {
				// fail
			}
		}
	}

	/**
	 * Acquires a DAO object for making queries to the database.
	 *
	 * @param dbClass the entity type
	 * @return the DAO object
	 */
	public static <E> Dao<E, Integer> acquireDao(Class dbClass) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				return DaoManager.createDao(conn, dbClass);
			} catch (SQLException e) {
				// fail
			}
		}
		return null;
	}
}
