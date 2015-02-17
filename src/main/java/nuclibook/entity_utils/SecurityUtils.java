package nuclibook.entity_utils;

import com.j256.ormlite.support.ConnectionSource;
import nuclibook.models.CannotHashPasswordException;
import nuclibook.models.Staff;
import nuclibook.server.SqlServerConnection;

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

}
