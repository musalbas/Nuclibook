package nuclibook.entity_utils;

import com.j256.ormlite.support.ConnectionSource;
import javafx.util.Pair;
import nuclibook.constants.C;
import nuclibook.constants.P;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import nuclibook.server.SqlServerConnection;
import spark.Response;
import spark.Session;

import java.util.HashMap;

/**
 * Utilities for managing user sessions and login tasks.
 */
public class SecurityUtils {

	private static HashMap<String, Pair<Long, Session>> oneOffTokens = new HashMap<>();

	private SecurityUtils() {
		// prevent instantiation
	}

	private static void setUser(Session session, Staff user) {
		session.attribute("user", user);
	}

	private static Staff getUser(Session session) {
		return session == null ? null : session.attribute("user");
	}

	/**
	 * Attempt a login.
	 *
	 * @param session The browsing session.
	 * @param username The username of the user.
	 * @param password The password of the user.
	 * @return The Staff object representing the logged in user, otherwise null if the login failed.
	 */
	public static Staff attemptLogin(Session session, String username, String password) {
		// set up server connection
		ConnectionSource conn = SqlServerConnection.acquireConnection();
		if (conn != null) {
			try {
				// search for user
				Staff staff = StaffUtils.getStaffByUsername(username);
				if (staff != null) {
					// check their password
					if (staff.checkPassword(password)) {

						// correct login!
						setUser(session, staff);

						// set session timeout
						session.maxInactiveInterval(C.AUTOMATIC_TIMEOUT);

						return staff;
					}
				}
			} catch (CannotHashPasswordException e) {
				// fail
			}
		}
		return null;
	}

	/**
	 * Check if the user is logged in.
	 *
	 * @param session The browsing session.
	 * @return true if the user is logged in, otherwise false.
	 */
	public static boolean checkLoggedIn(Session session) {
		return getUser(session) != null;
	}

	/**
	 * De-authenticate the user.
	 *
	 * @param session The browsing session.
	 */
	public static void destroyLogin(Session session) {
		session.invalidate();
	}

	/**
	 * Get the user logged in.
	 *
	 * @param session The browsing session.
	 * @return The Staff object for the logged in user.
	 */
	public static Staff getCurrentUser(Session session) {
		return getUser(session);
	}

	/**
	 * Redirect the user to an "access denied" page if the user doesn't have the required permissions.
	 *
	 * @param user The Staff object for the user.
	 * @param p The required permission
	 * @param response The server response object.
	 * @return false if the user does not have the required permission (and redirect the user to an "access denied" page in the response), otherwise true.
	 */
	public static boolean requirePermission(Staff user, P p, Response response) {
		if (user == null || !user.hasPermission(p)) {
			try {
				response.redirect("/access-denied");
			} catch (IllegalStateException e) {
				// already redirecting
			}
			return false;
		}
		return true;
	}

	/**
	 * Check that a new password fits within the new password policy.
	 *
	 * @param user The Staff object representing the user.
	 * @param password The new password for the user.
	 * @return null if the new password is valid, otherwise a String that states why the password does not comply with the new password policy.
	 * @throws CannotHashPasswordException
	 */
	public static String validateNewPassword(Staff user, String password) throws CannotHashPasswordException {
		if (password.length() < 6) {
			return "Password must be at least 6 characters long.";
		} else if (user.isInLastPasswords(password)) {
			return "Password must not be the same as the last few passwords.";
		}

		return null;
	}

	/**
	 * Set a one-off security token to identify the user's session.
	 *
	 * @param token The token.
	 * @param session The browsing session.
	 */
	public static void setOneOffToken(String token, Session session) {
		oneOffTokens.put(token, new Pair<>(System.currentTimeMillis(), session));
	}

	/**
	 * Check that a one-off security token is valid and return the session associated with it.
	 *
	 * @param token The token.
	 * @return The Session if the token is valid, otherwise null.
	 */
	public static Session checkOneOffSession(String token) {
		Pair<Long, Session> found = oneOffTokens.get(token);
		if (found == null) return null;
		if (found.getKey() + (2 * 60 * 1000) < System.currentTimeMillis()) {
			oneOffTokens.remove(token);
			return null;
		}
		return found.getValue();
	}

}
