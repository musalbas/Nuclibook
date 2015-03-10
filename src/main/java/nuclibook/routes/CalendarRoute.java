package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Booking;
import nuclibook.models.BookingSection;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarRoute extends DefaultRoute {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle();

        // security check
        //TODO - what will this be? It's both viewing and editing an appointment, since you can book from the calendar.

        if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENTS, response)) return null;
        String start = request.queryParams("start");
        String end = request.queryParams("end");
        DateTime startDate = new DateTime(start);
        DateTime endDate = new DateTime(end);

        List<DateTime> allDays = new ArrayList<DateTime>();
        DateTime toAdd = startDate;
        for (int i = startDate.getDayOfWeek(); i <= endDate.getDayOfWeek(); i++) {
            allDays.add(toAdd);
            toAdd = startDate.plusDays(1);
        }

        //magix to retrieve bookings by date
        //TODO: magician, pls provide ;(((
        List<Booking> bookings = null;//BookingUtils.getBookingsByDateRange(startDate, endDate);

        //TODO create a HashMap for [Day, List<Booking] pairs

        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("{ 'week': [");

        //put in bookings
        for (int i = 0; i < allDays.size(); i++) {
            jsonOutput.append("{");
            jsonOutput.append("'day': '" + allDays.get(i) + "'");
            jsonOutput.append("'bookings': [");

            for (int j = 0; j < bookings.size(); j++) {
                jsonOutput.append("{");
                jsonOutput.append("'patientId': '" + bookings.get(i).getPatient().getId() + "'");
                jsonOutput.append("'therapyName': '" + bookings.get(i).getTherapy().getName() + "'");
                jsonOutput.append("'bookingSections': [");

                List<BookingSection> bookingSections = bookings.get(i).getBookingSections();
                for (int k = 0; k < bookingSections.size(); k++) {
                    jsonOutput.append("{");
                    jsonOutput.append("'start-time': '" + bookingSections.get(i).getStart() + "'");
                    jsonOutput.append("'end-time': '" + bookingSections.get(i).getEnd() + "'");

                    if (j == bookingSections.size() - 1) {
                        jsonOutput.append("}");
                    } else {
                        jsonOutput.append("}, ");
                    }
                }
                //close booking sections array
                jsonOutput.append("]");

                if (j == bookings.size() - 1) {
                    jsonOutput.append("}");
                } else {
                    jsonOutput.append("}, ");
                }
            }
            //close bookings array
            jsonOutput.append("]");

            if (i == allDays.size() - 1) {
                jsonOutput.append("}");
            } else {
                jsonOutput.append("}, ");
            }

        }
        //close days array and week immediately after
        jsonOutput.append("]}");

        return jsonOutput;
    }
}
