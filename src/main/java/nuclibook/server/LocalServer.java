package nuclibook.server;

import nuclibook.routes.BlankRoute;
import spark.Spark;

public class LocalServer {

	public static void main(String... args) {
		/*
		SERVER SETTINGS
		 */

		// static files folder
		Spark.staticFileLocation("/static");

		// page security
		Spark.before((request, response) -> {
			boolean authenticated = false;

			// check if they are accessing a non-secure page
			String path = request.pathInfo();
			if (path.startsWith("/login")
					|| path.startsWith("/css")
					|| path.startsWith("/js")) {
				// nothing more to do - everything is fine
				return;
			}

			// TODO: check if they are authenticated

			// not authenticated?
			if (!authenticated) {
				// send them back to the login page
				response.redirect("/login");
			}
		});

		/*
		ROUTES
		 */

		Spark.get("/", new BlankRoute());

		Spark.get("/login", new BlankRoute("login"));
	}

}
