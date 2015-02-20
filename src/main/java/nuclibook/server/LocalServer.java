package nuclibook.server;

import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.routes.*;
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
		Spark.get("/", new DashboardRoute());

		// login/logout
		Spark.get("/login", new LoginRoute(RequestType.GET));
		Spark.post("/login", new LoginRoute(RequestType.POST));
		Spark.get("/logout", new LogoutRoute());

        // action logs
        Spark.get("/action-log", new ActionLogRoute());

		// entity CRUD
		Spark.post("/entity-update", new CrudCreateUpdateRoute());
		Spark.post("/entity-delete", new CrudDeleteRoute());

		// cameras
		Spark.get("/cameras", new CamerasRoute());

		// cameras
		Spark.get("/medicines", new MedicinesRoute());

		// staff
		Spark.get("/staff", new StaffRoute());

		// therapies
		Spark.get("/therapies", new TherapiesRoute());

		// debugging
		Spark.get("/htmltest/:file", new HtmlTestRoute());
	}

}
