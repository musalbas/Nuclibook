package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.BookingSection;
import nuclibook.models.Patient;
import nuclibook.models.Staff;
import nuclibook.models.Therapy;
import nuclibook.server.HtmlRenderer;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This route provides the first stage of the booking process (select camera, select staff, select tracer)
 */
public class NewBookingRouteStage2 extends DefaultRoute {

	/**
	 * Handles the second stage of the booking process
	 * @param request  Information sent by the client
	 * @param response Information sent to the client
	 * @return A fully rendered version of the front-end for the second stage of the booking process
	 * @throws Exception if something goes wrong, for example, loss of connection with a server
	 */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_PATIENT_LIST, response)) return null;
		if (!SecurityUtils.requirePermission(user, P.VIEW_THERAPIES, response)) return null;
		if (!SecurityUtils.requirePermission(user, P.EDIT_APPOINTMENTS, response)) return null;

		// get JSON from submission
		String rawJson = request.queryParams("jsonFromStage1");

		// parse JSON
		Patient patient;
		Therapy therapy;
		List<BookingSection> displayBookingSections = new ArrayList<>();
		JSONArray bookingSectionJsonArray;
		try {
			// get patient and therapy
			JSONObject mainJsonObject = new JSONObject(rawJson);
			patient = PatientUtils.getPatient(mainJsonObject.getInt("patientId"));
			therapy = TherapyUtils.getTherapy(mainJsonObject.getInt("therapyId"));
			if (patient == null || therapy == null) throw new NullPointerException();

			// get booking sections
			bookingSectionJsonArray = mainJsonObject.getJSONArray("bookingSections");
			JSONObject bookingSectionJsonObject;
			BookingSection tempBookingSection;
			for (int i = 0; i < bookingSectionJsonArray.length(); ++i) {
				bookingSectionJsonObject = bookingSectionJsonArray.getJSONObject(i);
				tempBookingSection = new BookingSection();
				tempBookingSection.setStart(new DateTime(bookingSectionJsonObject.getString("startTime")));
				tempBookingSection.setEnd(new DateTime(bookingSectionJsonObject.getString("endTime")));
				displayBookingSections.add(tempBookingSection);
			}

			// sort booking sections by date
			displayBookingSections.sort(new Comparator<BookingSection>() {
				@Override
				public int compare(BookingSection o1, BookingSection o2) {
					return o1.getStart().compareTo(o2.getStart());
				}
			});
		} catch (JSONException | NullPointerException e) {
			e.printStackTrace();
			response.redirect("/");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("new-booking-stage-2.html");

		// add booking info
		renderer.setField("patient-name", patient.getName());
		renderer.setField("patient-id", patient.getId());
		renderer.setField("therapy-name", therapy.getName());
		renderer.setField("therapy-id", therapy.getId());
		renderer.setField("booking-sections-json", URLEncoder.encode(bookingSectionJsonArray.toString(), "UTF-8"));
		renderer.setCollection("booking-sections", displayBookingSections);

		// add cameras
		renderer.setCollection("cameras", CameraUtils.getCamerasForTherapy(therapy));

		// add tracers
		renderer.setCollection("tracers", TracerUtils.getAllTracers(true));
		renderer.setField("default-tracer-id", therapy.getTracerRequired().getId());

		// add tracer dose
		renderer.setField("therapy-tracer-dose", therapy.getTracerDose());

		// add tracer order date
		renderer.setField("tracer-order-due", displayBookingSections.isEmpty() ? "" : displayBookingSections.get(0).getStart().minusDays(therapy.getTracerRequired().getOrderTime()).toString("YYYY-MM-dd"));

		// add staff
		List<Staff> allStaff = StaffUtils.getAllStaff(true);
		Collections.sort(allStaff, new Comparator<Staff>() {
			@Override
			public int compare(Staff o1, Staff o2) {
				return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
			}
		});
		renderer.setCollection("staff", allStaff);

		return renderer.render();
	}
}
