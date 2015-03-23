package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Booking;
import nuclibook.models.Patient;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * The class handles user's request to view patient details.
 */
public class PatientDetailsRoute extends DefaultRoute {
    /**
     * Handles user's request to view patient details.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the patient-details.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.VIEW_PATIENT_DETAILS, response)) {
            //check which patient was being viewed
            String patientId = (request.params(":patientid:")) == null ? 0 + "" : request.params(":patientid:");
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_PATIENT, Integer.parseInt(patientId), "Failed as user does not have permissions for this action");
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
        renderer.setCollection("patients", new ArrayList<Patient>() {{
            add(patient);
        }});

        // add patient bookings
        List<Booking> bookings = BookingUtils.getBookingsByPatientId(patient.getId());
        renderer.setCollection("bookings", bookings);

        ActionLogger.logAction(user, ActionLogger.VIEW_PATIENT, patient.getId());

        return renderer.render();
    }
}
