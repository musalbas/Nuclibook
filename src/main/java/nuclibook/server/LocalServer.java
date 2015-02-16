package nuclibook.server;

import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.routes.BlankRoute;
import nuclibook.routes.HtmlTestRoute;
import nuclibook.routes.LoginRoute;
import nuclibook.routes.LogoutRoute;
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
					|| path.startsWith("/js")
					|| path.startsWith("/font-awesome")) {
				// nothing more to do - everything is fine
				return;
			}

			// not authenticated?
			if (!SecurityUtils.checkLoggedIn()) {
				// send them back to the login page
				response.redirect("/login");
			}
		});

		// prevent viewing pages after logout
		Spark.after((request, response) -> {
			response.header("Cache-Control", "no-cache, no-store, must-revalidate");
			response.header("Pragma", "no-cache");
			response.header("Expires", "0");
		});

		/*
		ROUTES
		 */

		// basic pages
		Spark.get("/", new BlankRoute("Home page"));

		// login/logout
		Spark.get("/login", new LoginRoute(RequestType.GET));
		Spark.post("/login", new LoginRoute(RequestType.POST));
		Spark.get("/logout", new LogoutRoute());

		// debugging
		Spark.get("/htmltest/:file", new HtmlTestRoute());
	}

}
