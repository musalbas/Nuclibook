package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.models.BookingSection;
import nuclibook.models.Patient;
import nuclibook.models.Therapy;
import nuclibook.server.HtmlRenderer;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class NewBookingRouteStage2 extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_LIST, response)) return null;
		if (!SecurityUtils.requirePermission(P.VIEW_THERAPIES, response)) return null;
		if (!SecurityUtils.requirePermission(P.EDIT_APPOINTMENTS, response)) return null;

		// TODO: get real JSON
		String rawJson = "{\n" +
				"    \"patientId\": 5,\n" +
				"    \"therapyId\": 1,\n" +
				"    \"bookingSections\": [\n" +
				"        {\n" +
				"            \"startTime\": \"2015-03-13T14:00:00.000\",\n" +
				"            \"endTime\": \"2015-03-13T14:20:00.000\"\n" +
				"        },\n" +
				"        {\n" +
				"            \"startTime\": \"2015-03-13T15:00:00.000\",\n" +
				"            \"endTime\": \"2015-03-13T16:00:00.000\"\n" +
				"        },\n" +
				"        {\n" +
				"            \"startTime\": \"2015-03-13T17:00:00.000\",\n" +
				"            \"endTime\": \"2015-03-13T17:30:00.000\"\n" +
				"        }\n" +
				"    ]\n" +
				"}";

		// parse JSON
		Patient patient;
		Therapy therapy;
		List<BookingSection> displayBookingSections = new ArrayList<>();
		try {
			// get patient and therapy
			JSONObject mainJsonObject = new JSONObject(rawJson);
			patient = PatientUtils.getPatient(mainJsonObject.getInt("patientId"));
			therapy = TherapyUtils.getTherapy(mainJsonObject.getInt("therapyId"));
			if (patient == null || therapy == null) throw new NullPointerException();

			// get booking sections
			JSONArray bookingSectionJsonArray = mainJsonObject.getJSONArray("bookingSections");
			JSONObject bookingSectionJsonObject;
			BookingSection tempBookingSection;
			for (int i = 0; i < bookingSectionJsonArray.length(); ++i) {
				bookingSectionJsonObject = bookingSectionJsonArray.getJSONObject(i);
				tempBookingSection = new BookingSection();
				tempBookingSection.setStart(new DateTime(bookingSectionJsonObject.getString("startTime")));
				tempBookingSection.setEnd(new DateTime(bookingSectionJsonObject.getString("endTime")));
				displayBookingSections.add(tempBookingSection);
			}
		} catch (JSONException | NullPointerException e) {
			response.redirect("/");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("new-booking-stage-2.html");

		// add booking info
		renderer.setField("patient-name", patient.getName());
		renderer.setField("therapy-name", therapy.getName());

		renderer.setCollection("booking-sections", displayBookingSections);

		return renderer.render();
	}
}
