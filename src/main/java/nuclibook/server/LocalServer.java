package nuclibook.server;

import nuclibook.constants.C;
import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.routes.*;
import org.apache.commons.configuration.ConfigurationException;
import spark.Spark;

public class LocalServer {

	public static void main(String... args) {
		/*
		SERVER SETTINGS
		 */

        //initialise constants
        try {
            C.initConstants();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
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
		Spark.get("/profile", new ProfileRoute());

		// action logs
		Spark.get("/action-log", new ActionLogRoute());

		// entity CRUD action routes
		Spark.post("/entity-update", new CrudCreateUpdateRoute());
		Spark.post("/entity-delete", new CrudDeleteRoute());

		// basic CRUD pages
		Spark.get("/cameras", new CamerasRoute());
		Spark.get("/camera-types", new CameraTypesRoute());
		Spark.get("/patients", new PatientsRoute());
		Spark.get("/staff", new StaffRoute());
		Spark.get("/staff-roles", new StaffRolesRoute());
		Spark.get("/therapies", new TherapiesRoute());
		Spark.get("/tracers", new TracersRoute());

		// staff absences and availabilities
		Spark.get("/select-staff/:target:", new SelectStaffRoute());
		Spark.get("/staff-absences/:staffid:", new StaffAbsencesRoute());
        Spark.get("/staff-availabilities/:staffid:", new StaffAvailabilitiesRoute());

        // bookings
        Spark.get("/appointments", new AppointmentsRoute());
        Spark.get("/booking-details/:bookingid:", new BookingDetailsRoute());

        // calendar week view
        Spark.get("/calendar", new CalendarRoute());

		// patients
		Spark.get("/patient-details/:patientid:", new PatientDetailsRoute());

		// debugging
		Spark.get("/htmltest/:file", new HtmlTestRoute());
	}

}
