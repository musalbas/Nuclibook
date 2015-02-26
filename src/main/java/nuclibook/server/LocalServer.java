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
					|| path.startsWith("/images")
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

		// security
		Spark.get("/access-denied", new AccessDeniedRoute());
		Spark.get("/login", new LoginRoute(RequestType.GET));
		Spark.post("/login", new LoginRoute(RequestType.POST));
		Spark.get("/logout", new LogoutRoute());

		// action logs
		Spark.get("/action-log", new ActionLogRoute());

		// entity CRUD action routes
		Spark.post("/entity-update", new CrudCreateUpdateRoute());
		Spark.post("/entity-delete", new CrudDeleteRoute());

		// basic CRUD pages
		Spark.get("/cameras", new CamerasRoute());
		Spark.get("/camera-types", new CameraTypesRoute());
		Spark.get("/medicines", new MedicinesRoute());
		Spark.get("/patients", new PatientsRoute());
		Spark.get("/staff", new StaffRoute());
		Spark.get("/staff-roles", new StaffRolesRoute());
		Spark.get("/therapies", new TherapiesRoute());

		// staff absences and availabilities
		Spark.get("/select-staff/:target:", new SelectStaffRoute());
		Spark.get("/staff-absences/:staffid:", new StaffAbsencesRoute());

		// debugging
		Spark.get("/htmltest/:file", new HtmlTestRoute());
	}

}
