package nuclibook.routes;

import nuclibook.models.BookingSection;
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
		int patientId, therapyId;
		List<BookingSection> displayBookingSections = new ArrayList<>();
		try {
			JSONObject mainJsonObject = new JSONObject(rawJson);
			patientId = mainJsonObject.getInt("patientId");
			therapyId = mainJsonObject.getInt("therapyId");
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
		} catch (JSONException e) {
			response.redirect("/");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("new-booking-stage-2.html");

		// add test fields
		renderer.setField("patient-id", patientId);
		renderer.setField("therapy-id", therapyId);
		renderer.setCollection("booking-sections", displayBookingSections);

		return renderer.render();
	}
}
