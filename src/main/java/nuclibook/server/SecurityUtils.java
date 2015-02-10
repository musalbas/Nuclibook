package nuclibook.server;

import nuclibook.models.User;

public class SecurityUtils {

	/* singleton pattern */

	private static User loggedInAs = null;

	private SecurityUtils() {
		// prevent instantiation
	}

	public static User attemptLogin(String username, String password) {
		if (username.equals("test") && password.equals("test")) {
			loggedInAs = new User();
			return loggedInAs;
		}
		return null;
	}

	public static boolean checkLoggedIn() {
		return loggedInAs != null;
	}

	public static void destroyLogin() {
		loggedInAs = null;
	}

	public static User getCurrentUser() {
		return loggedInAs;
	}

}
