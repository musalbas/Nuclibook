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
			// get path
			String path = request.pathInfo();

			// check if they are accessing non-page
			if (path.startsWith("/css")
					|| path.startsWith("/images")
					|| path.startsWith("/js")
					|| path.startsWith("/font-awesome")) {
				// nothing more to do - everything is fine
				return;
			}

			// check for a password force-change
			if (SecurityUtils.checkLoggedIn()
					&& SecurityUtils.getCurrentUser() != null
					&& SecurityUtils.getCurrentUser().getDaysRemainingToPasswordChange() < 1
					&& !path.startsWith("/profile")) {
				response.redirect("/profile?changepw=1&force=1");
			}

			// check if they are accessing a non-secure page
			if (path.startsWith("/login")) {
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
			// get path
			String path = request.pathInfo();

			// don't apply this for resources
			if (path.startsWith("/css")
					|| path.startsWith("/images")
					|| path.startsWith("/js")
					|| path.startsWith("/font-awesome")) {
				// nothing more to do - everything is fine
				return;
			}

			response.header("Cache-Control", "no-cache, no-store, must-revalidate");
			response.header("Pragma", "no-cache");
			response.header("Expires", "0");
		});

		/*
		ROUTES
		 */

		// basic pages
		Spark.get("/", new DashboardRoute());

        // day summary
        Spark.get("/day-summary", new DaySummaryRoute());

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
		Spark.get("/new-booking-1", new NewBookingRouteStage1());
		Spark.post("/new-booking-2", new NewBookingRouteStage2());
		Spark.post("/new-booking-3", new NewBookingRouteStage3());
		Spark.get("/bookings", new BookingsRoute());
		Spark.get("/booking-details/:bookingid:", new BookingDetailsRoute());
		Spark.get("/booking-details/:bookingid:/:newstatus:", new BookingDetailsRoute());

		// AJAX routes
		Spark.get("/ajax/calendar-data", new AjaxCalendarDataRoute());
		Spark.get("/ajax/patient-data", new AjaxPatientDataRoute());

		// tracer orders
		Spark.get("/tracer-orders", new TracerOrdersRoute());
		Spark.get("/tracer-order-details/:tracerorderid:", new TracerOrderDetailsRoute());
		Spark.get("/tracer-order-details/:tracerorderid:/:newstatus:", new TracerOrderDetailsRoute());

		// patients
		Spark.get("/patient-details/:patientid:", new PatientDetailsRoute());

		// export
		Spark.get("/export/:file:", new ExportRoute());

		// import
		Spark.post("/import", new ImportRoute());
	}

}
