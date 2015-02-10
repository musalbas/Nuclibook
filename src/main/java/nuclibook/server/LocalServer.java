package nuclibook.server;

import nuclibook.constants.RequestType;
import nuclibook.routes.BlankRoute;
import nuclibook.routes.HtmlTestRoute;
import nuclibook.routes.LoginRoute;
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
					|| path.startsWith("/htmltest")
					|| path.startsWith("/css")
					|| path.startsWith("/js")) {
				// nothing more to do - everything is fine
				return;
			}

			// not authenticated?
			if (!SecurityUtils.checkLoggedIn()) {
				// send them back to the login page
				response.redirect("/login");
			}
		});

		/*
		ROUTES
		 */

		// basic pages
		Spark.get("/", new BlankRoute("Home page"));

		// login
		Spark.get("/login", new LoginRoute(RequestType.GET));
		Spark.post("/login", new LoginRoute(RequestType.POST));

		// debugging
		Spark.get("/htmltest/:file", new HtmlTestRoute());

	}

}
