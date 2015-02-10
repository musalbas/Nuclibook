package nuclibook.server;

import nuclibook.models.User;

public class SecurityUtils {

	public static User attemptLogin(String username, String password) {
		if (username.equals("test") && password.equals("test")) {
			return new User();
		}
		return null;
	}

	public static boolean checkLoggedIn() {
		return false;
	}

	public static void destroyLogin() {

	}

}
