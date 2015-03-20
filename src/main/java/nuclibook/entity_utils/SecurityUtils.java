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

						// set session timeout time
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

	public static boolean checkLoggedIn(Session session) {
		return getUser(session) != null;
	}

	public static void destroyLogin(Session session) {
		session.invalidate();
	}

	public static Staff getCurrentUser(Session session) {
		return getUser(session);
	}

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

	public static String validateNewPassword(Staff user, String password) throws CannotHashPasswordException {
		if (password.length() < 6) {
			return "Password must be at least 6 characters long.";
		} else if (user.isInLastPasswords(password)) {
			return "Password must not be the same as the last few passwords.";
		}

		return null;
	}

	public static void setOneOffToken(String token, Session session) {
		oneOffTokens.put(token, new Pair<>(System.currentTimeMillis(), session));
	}

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
