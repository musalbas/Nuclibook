package nuclibook.routes;

import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.StaffAbsenceUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.Booking;
import nuclibook.models.StaffAbsence;
import nuclibook.models.TracerOrder;
import nuclibook.server.HtmlRenderer;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class DaySummaryRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("day-summary.html");

		// Date today
		DateTime today = new DateTime();
		DateTime todayStart = today.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
		DateTime todayEnd = today.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);
		DateTime tomorrowEnd = today.plusDays(1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999);

		// get bookings happening today
		//TODO: Change this to read selected day rather than today
		List<Booking> bookings = BookingUtils.getBookingsByDateRange(todayStart, todayEnd);
		ArrayList<Booking> confirmedBookings = new ArrayList<>();
		for (Booking b : bookings) {
			if (b.getStatus().equals("confirmed")) {
				confirmedBookings.add(b);
			}
		}
		renderer.setCollection("bookings", confirmedBookings);

		// get unordered tracers that are required in the next two days
		List<TracerOrder> unorderedTracers = TracerOrderUtils.getTracerOrdersRequiredByDay(today, true);
		unorderedTracers.addAll(TracerOrderUtils.getTracerOrdersRequiredByDay(today.plusDays(1), true));
		if (!unorderedTracers.isEmpty()) {
			renderer.setCollection("unordered-tracers", unorderedTracers);
		}

		// get today's and tomorrow's absences
		List<StaffAbsence> staffAbsences = StaffAbsenceUtils.getStaffAbsencesByDateRange(todayStart, tomorrowEnd);
		if (!staffAbsences.isEmpty()) {
			for (StaffAbsence sa : staffAbsences) {
				String absencesAsString = "";

				// get details needed
				String staffName = "<strong>" + sa.getStaff().getName() + "</strong>";
				DateTime f = sa.getFrom();
				DateTime t = sa.getTo();

				// what type of absence is this?
				if (f.isAfter(todayStart) && t.isBefore(todayEnd)) {
					// entirely within today
					absencesAsString += "<li class=\"list-group-item\">\n";
					absencesAsString += staffName + " is absent from " + f.toString("HH:mm") + " to " + t.toString("HH:mm");
					absencesAsString += "</li>";
				} else if (f.isBefore(todayStart) && t.isAfter(todayEnd)) {
					// completely overlaps today
					absencesAsString += "<li class=\"list-group-item\">\n";
					absencesAsString += staffName + " is absent all day";
					absencesAsString += "</li>";
				} else if (f.isAfter(todayStart) && t.isAfter(todayEnd)) {
					// starts today, ends later
					absencesAsString += "<li class=\"list-group-item\">\n";
					absencesAsString += staffName + " is absent from " + f.toString("HH:mm");
					absencesAsString += "</li>";
				} else if (f.isBefore(todayStart) && t.isBefore(todayEnd)) {
					// started earlier, ends today
					absencesAsString += "<li class=\"list-group-item\">\n";
					absencesAsString += staffName + " is absent until " + t.toString("HH:mm");
					absencesAsString += "</li>";
				}

				// add to collection
				renderer.setField("absences-as-string", absencesAsString);
			}
		}

		return renderer.render();
	}
}
