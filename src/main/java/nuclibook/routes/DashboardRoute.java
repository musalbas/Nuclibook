package nuclibook.routes;

import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.StaffAbsenceUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.Booking;
import nuclibook.models.StaffAbsence;
import nuclibook.models.TracerOrder;
import nuclibook.server.HtmlRenderer;
import nuclibook.server.Renderable;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle();

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("dashboard.html");

		// get unconfirmed bookings
		List<Booking> unconfirmedBookings = BookingUtils.getBookingsByStatus("unconfirmed");
		renderer.setCollection("unconfirmed-bookings", unconfirmedBookings);

		// get unordered tracers
		List<TracerOrder> unorderedTracers = TracerOrderUtils.getTracerOrdersByStatus("pending");
		renderer.setCollection("unordered-tracers", unorderedTracers);

		// day summary items
		List<DaySummaryItem> daySummaryItems = new ArrayList<>();

		// date objects for day summary searches
		DateTime today = new DateTime();
		DateTime tomorrow = today.plusDays(1);
		DateTime todayStart = today.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		DateTime todayEnd = today.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
		DateTime tomorrowStart = tomorrow.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		DateTime tomorrowEnd = tomorrow.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);

		// day summary: bookings today
		List<Booking> bookingsToday = BookingUtils.getBookingsByDateRange(todayStart, todayEnd);
		DaySummaryItem dsiBookingsToday;
		if (bookingsToday == null || bookingsToday.size() == 0) {
			dsiBookingsToday = new DaySummaryItem("There are <strong>no bookings</strong> today");
		} else if (bookingsToday.size() == 1) {
			dsiBookingsToday = new DaySummaryItem("There is <strong>1 booking</strong> today");
		} else {
			dsiBookingsToday = new DaySummaryItem("There are <strong>" + daySummaryItems.size() + " bookings</strong> today");
		}
		dsiBookingsToday.setLink("/bookings?date=" + today.toString("YYYY-MM-dd"));
		dsiBookingsToday.setBadgeText("click to view");
		dsiBookingsToday.setIcon("fa-clock-o");
		daySummaryItems.add(dsiBookingsToday);

		// day summary: bookings tomorrow
		List<Booking> bookingsTomorrow = BookingUtils.getBookingsByDateRange(tomorrowStart, tomorrowEnd);
		DaySummaryItem dsiBookingsTomorrow;
		if (bookingsTomorrow == null || bookingsTomorrow.size() == 0) {
			dsiBookingsTomorrow = new DaySummaryItem("There are <strong>no bookings</strong> tomorrow");
		} else if (bookingsTomorrow.size() == 1) {
			dsiBookingsTomorrow = new DaySummaryItem("There is <strong>1 booking</strong> tomorrow");
		} else {
			dsiBookingsTomorrow = new DaySummaryItem("There are <strong>" + bookingsTomorrow.size() + " bookings</strong> tomorrow");
		}
		dsiBookingsTomorrow.setLink("/bookings?date=" + tomorrow.toString("YYYY-MM-dd"));
		dsiBookingsTomorrow.setBadgeText("click to view");
		dsiBookingsTomorrow.setIcon("fa-clock-o");
		daySummaryItems.add(dsiBookingsTomorrow);

		// day summary: tracer orders today
		List<TracerOrder> tracerOrdersRequiredToday = TracerOrderUtils.getTracerOrdersRequiredByDay(today);
		DaySummaryItem dsiTracerOrdersToday;
		if (tracerOrdersRequiredToday == null || tracerOrdersRequiredToday.size() == 0) {
			dsiTracerOrdersToday = new DaySummaryItem("There are <strong>no tracer orders</strong> required today");
		} else if (tracerOrdersRequiredToday.size() == 1) {
			dsiTracerOrdersToday = new DaySummaryItem("There is <strong>1 tracer order</strong> required today");
		} else {
			dsiTracerOrdersToday = new DaySummaryItem("There are <strong>" + tracerOrdersRequiredToday.size() + " tracer orders</strong> required today");
		}
		dsiTracerOrdersToday.setLink("/tracer-orders?ordertoday=1");
		dsiTracerOrdersToday.setBadgeText("click to view");
		dsiTracerOrdersToday.setIcon("fa-flask");
		daySummaryItems.add(dsiTracerOrdersToday);

		// day summary: tracer orders tomorrow
		List<TracerOrder> tracerOrdersRequiredTomorrow = TracerOrderUtils.getTracerOrdersRequiredByDay(tomorrow);
		DaySummaryItem dsiTracerOrdersTomorrow;
		if (tracerOrdersRequiredTomorrow == null || tracerOrdersRequiredTomorrow.size() == 0) {
			dsiTracerOrdersTomorrow = new DaySummaryItem("There are <strong>no tracer orders</strong> required tomorrow");
		} else if (tracerOrdersRequiredTomorrow.size() == 1) {
			dsiTracerOrdersTomorrow = new DaySummaryItem("There is <strong>1 tracer order</strong> required tomorrow");
		} else {
			dsiTracerOrdersTomorrow = new DaySummaryItem("There are <strong>" + tracerOrdersRequiredTomorrow.size() + " tracer orders</strong> required tomorrow");
		}
		dsiTracerOrdersTomorrow.setLink("/tracer-orders?ordertomorrow=1");
		dsiTracerOrdersTomorrow.setBadgeText("click to view");
		dsiTracerOrdersTomorrow.setIcon("fa-flask");
		daySummaryItems.add(dsiTracerOrdersTomorrow);

		// day summary: absences
		List<StaffAbsence> staffAbsences = StaffAbsenceUtils.getStaffAbsencesByDateRange(todayStart, tomorrowEnd);
		for (StaffAbsence sa : staffAbsences) {
			// set up DSI
			DaySummaryItem dsi = new DaySummaryItem();
			dsi.setIcon("fa-times-circle");

			// get details needed
			String staffName = "<strong>" + sa.getStaff().getName() + "</strong>";
			DateTime f = sa.getFrom();
			DateTime t = sa.getTo();

			// what type of absence is this?
			if (f.isAfter(todayStart) && t.isBefore(todayEnd)) {
				// entirely within today
				dsi.setMessage(staffName + " is absent from " + f.toString("HH:mm") + " to " + t.toString("HH:mm"));
			} else if (f.isBefore(todayStart) && t.isAfter(todayEnd)) {
				// completely overlaps today
				dsi.setMessage(staffName + " is absent all day");
			} else if (f.isAfter(todayStart) && t.isAfter(todayEnd)) {
				// starts today, ends later
				dsi.setMessage(staffName + " is absent from " + f.toString("HH:mm"));
			} else if (f.isBefore(todayStart) && t.isBefore(todayEnd)) {
				// started earlier, ends today
				dsi.setMessage(staffName + " is absent until " + t.toString("HH:mm"));
			} else {
				dsi = null;
			}

			// add to collection
			if (dsi != null) daySummaryItems.add(dsi);
		}

		// add day summary to renderer
		renderer.setCollection("day-summary", daySummaryItems);

		return renderer.render();
	}

	private class DaySummaryItem implements Renderable {

		private String message;
		private String link = "javascript:;";
		private String icon = "";
		private String badgeType = "default";
		private String badgeText = null;

		public DaySummaryItem() {
		}

		public DaySummaryItem(String message) {
			this.message = message;
		}

		public DaySummaryItem(String message, String link) {
			this.message = message;
			this.link = link;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getBadgeType() {
			return badgeType;
		}

		public void setBadgeType(String badgeType) {
			this.badgeType = badgeType;
		}

		public String getBadgeText() {
			return badgeText;
		}

		public void setBadgeText(String badgeText) {
			this.badgeText = badgeText;
		}

		@Override
		public HashMap<String, String> getHashMap() {
			return new HashMap<String, String>() {{
				put("message", getMessage());
				put("link", getLink());
				put("icon", getIcon());
				put("badge-type", getBadgeType());
				put("badge-text", getBadgeText());
				put("has-badge", getBadgeText() == null ? "no" : "yes");
			}};
		}
	}
}
