package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.Camera;
import nuclibook.models.Patient;
import nuclibook.models.Therapy;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class NewBookingRouteStage1 extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_LIST, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_CREATE_BOOKING, 0, "Failed as user does not have permissions to view all patients");
            return null;
        }
		if (!SecurityUtils.requirePermission(P.VIEW_THERAPIES, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_CREATE_BOOKING, 0, "Failed as user does not have permissions to view all therapies");
            return null;
        }
		if (!SecurityUtils.requirePermission(P.EDIT_APPOINTMENTS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_CREATE_BOOKING, 0, "Failed as user does not have permissions to edit or create bookings");
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
