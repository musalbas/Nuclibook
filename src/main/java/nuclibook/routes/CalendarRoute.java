package nuclibook.routes;

import nuclibook.constants.P;
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
        if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENTS, response)) return "no_permission";

        // get start/end date
        String start = request.queryParams("start");
        String end = request.queryParams("end");
        DateTime startDate = new DateTime(start + " 00:00:00");
        DateTime endDate = new DateTime(end + " 23:59:59");

        // get bookings between start/end dates
        List<Booking> bookings = BookingUtils.getBookingsByDateRange(startDate, endDate);

        //build json
        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("{ 'bookings': [");

        for (int i = 0; i < bookings.size(); i++) {

            if (i == 0) {
                jsonOutput.append("{");
            } else {
                jsonOutput.append(", { ");
            }

            jsonOutput.append("'patientId': '").append(bookings.get(i).getPatient().getId()).append("'");
            jsonOutput.append("'therapyName': '").append(bookings.get(i).getTherapy().getName().replace("'", "\\'")).append("'");
            jsonOutput.append("'bookingSections': [");

            List<BookingSection> bookingSections = bookings.get(i).getBookingSections();
            for (int j = 0; j < bookingSections.size(); j++) {

                if (j == 0) {
                    jsonOutput.append("{");
                } else {
                    jsonOutput.append(", { ");
                }

                jsonOutput.append("'startTime': '").append(bookingSections.get(i).getStart().toString("YYYY-MM-dd HH:mm") + "'");
                jsonOutput.append("'endTime': '").append(bookingSections.get(i).getEnd().toString("YYYY-MM-dd HH:mm") + "'");
                jsonOutput.append("}");
            }

            jsonOutput.append("]}");
        }

        jsonOutput.append("]}");

        return jsonOutput;
    }
}
