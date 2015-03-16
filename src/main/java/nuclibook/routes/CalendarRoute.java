package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Booking;
import nuclibook.models.BookingSection;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.List;

public class CalendarRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENTS, response)) {
            ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_BOOKING_CALENDAR, 0, "Failed as user does not have permissions for this action");
            return "no_permission";
        }

		// get start/end date
		String start = request.queryParams("start");
		String end = request.queryParams("end");
		DateTime startDate = new DateTime(start + "T00:00:00.000");
		DateTime endDate = new DateTime(end + "T23:59:59.999");

		// get bookings between start/end dates
		List<Booking> bookings = BookingUtils.getBookingsByDateRange(startDate, endDate);

		// build json
		StringBuilder jsonOutput = new StringBuilder();
		jsonOutput.append("{ \"bookings\": [");

		for (int i = 0; i < bookings.size(); i++) {
			if (i == 0) {
				jsonOutput.append("{");
			} else {
				jsonOutput.append(", { ");
			}

			jsonOutput.append(" \"patientName\": \"").append(bookings.get(i).getPatient().getName()).append("\",");
			jsonOutput.append("\"therapyName\": \"").append(bookings.get(i).getTherapy().getName().replace("\"", "\\\"")).append("\",");
			jsonOutput.append("\"bookingSections\": [");

			List<BookingSection> bookingSections = bookings.get(i).getBookingSections();
			for (int j = 0; j < bookingSections.size(); j++) {
				if (j == 0) {
					jsonOutput.append("{");
				} else {
					jsonOutput.append(", { ");
				}

				jsonOutput.append("\"startTime\": \"").append(bookingSections.get(j).getStart().toString("YYYY-MM-dd HH:mm")).append("\",");
				jsonOutput.append("\"endTime\": \"").append(bookingSections.get(j).getEnd().toString("YYYY-MM-dd HH:mm")).append("\"");
				jsonOutput.append("}");
			}

			jsonOutput.append("]}");
		}
		jsonOutput.append("]}");

        ActionLogger.logAction(ActionLogger.VIEW_BOOKING_CALENDAR, 0);

		return jsonOutput;
	}
}
