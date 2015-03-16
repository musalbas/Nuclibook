package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Booking;
import nuclibook.models.Patient;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class PatientDetailsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_DETAILS, response)) {
            //check which patient was being viewed
            String patientId = (request.params(":patientid:")) == null ? 0 + "" : request.params(":patientid:");
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_PATIENT, Integer.parseInt(patientId) ,"Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("patient-details.html");

		// add patient
		Patient patient = PatientUtils.getPatient(request.params(":patientid:"));
		if (patient == null) {
			renderer.setField("no-patient", "yes");
			return renderer.render();
		}

		renderer.setField("no-patient", "no");
		renderer.setBulkFields(patient.getHashMap());

		// add pseudo-collection for this patient
		renderer.setCollection("patients", new ArrayList<Patient>(){{
			add(patient);
		}});

		// add patient bookings
		List<Booking> bookings = BookingUtils.getBookingsByPatientId(patient.getId());
		renderer.setCollection("bookings", bookings);

        ActionLogger.logAction(ActionLogger.VIEW_PATIENT, patient.getId());

		return renderer.render();
	}
}
