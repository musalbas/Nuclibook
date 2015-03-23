package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.Camera;
import nuclibook.models.Patient;
import nuclibook.models.Staff;
import nuclibook.models.Therapy;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * This route provides the first stage of the booking process (select patient, select therapy, select date periods)
 */
public class NewBookingRouteStage1 extends DefaultRoute {

	/**
	 * Handles the first stage of the booking process
	 * @param request  Information sent by the client
	 * @param response Information sent to the client
	 * @return A fully rendered version of the front-end for the first stage of the booking process
	 * @throws Exception if something goes wrong, for example, loss of connection with a server
	 */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_PATIENT_LIST, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_CREATE_BOOKING, 0, "Failed as user does not have permissions to view all patients");
            return null;
        }
		if (!SecurityUtils.requirePermission(user, P.VIEW_THERAPIES, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_CREATE_BOOKING, 0, "Failed as user does not have permissions to view all therapies");
            return null;
        }
		if (!SecurityUtils.requirePermission(user, P.EDIT_APPOINTMENTS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_CREATE_BOOKING, 0, "Failed as user does not have permissions to edit or create bookings");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("new-booking-stage-1.html");

		// get patients and add to renderer
		List<Patient> allPatients = PatientUtils.getAllPatients(true);
		renderer.setCollection("patients", allPatients);

		// get therapies and add to renderer
		List<Therapy> allTherapies = TherapyUtils.getAllTherapies(true);
		renderer.setCollection("therapies", allTherapies);

		// get cameras
		List<Camera> cameras = CameraUtils.getAllCameras(true);
		renderer.setCollection("cameras", cameras);

		return renderer.render();
	}
}
