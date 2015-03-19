package nuclibook.entity_utils;

import com.j256.ormlite.support.ConnectionSource;
import nuclibook.constants.C;
import nuclibook.constants.P;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import nuclibook.server.SqlServerConnection;
import spark.Response;
import spark.Session;

public class SecurityUtils {

	private SecurityUtils() {
		// prevent instantiation
	}

	private static void setUser(Session session, Staff user) {
		session.attribute("user", user);
	}

	private static Staff getUser(Session session) {
		return session.attribute("user");
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

}
