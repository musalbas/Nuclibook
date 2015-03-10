package nuclibook.entity_utils;

import com.j256.ormlite.support.ConnectionSource;
import nuclibook.constants.P;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import nuclibook.server.SqlServerConnection;
import spark.Response;

public class SecurityUtils {

	/* singleton pattern */

	private static Staff loggedInAs = null;

	private SecurityUtils() {
		// prevent instantiation
	}

	public static Staff attemptLogin(String username, String password) {
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
						loggedInAs = staff;
						return staff;
					}
				}
			} catch (CannotHashPasswordException e) {
				// fail
			}
		}
		return null;
	}

	public static boolean checkLoggedIn() {
		return loggedInAs != null;
	}

	public static void destroyLogin() {
		loggedInAs = null;
	}

	public static Staff getCurrentUser() {
		return loggedInAs;
	}

	public static boolean requirePermission(P p, Response response) {
		if (loggedInAs == null || !loggedInAs.hasPermission(p)) {
			try {
				response.redirect("/access-denied");
			} catch (IllegalStateException e) {
				// already redirecting
			}
			return false;
		}
		return true;
	}

	public static String validateNewPassword(Staff staff, String password) {
		if (password.length() < 60) {
			return "Password must be at least 6 characters long.";
		}

		return null;
	}

}
