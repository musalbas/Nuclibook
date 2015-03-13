package nuclibook.routes;

import nuclibook.entity_utils.BookingUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.Booking;
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

		// day summary: bookings
		DateTime today = new DateTime();
		List<Booking> bookingsToday = BookingUtils.getBookingsByDateRange(
				today.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0),
				today.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
		);
		DateTime tomorrow = today.plusDays(1);
		List<Booking> bookingsTomorrow = BookingUtils.getBookingsByDateRange(
				tomorrow.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0),
				tomorrow.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
		);

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

		// add day summary to renderer
		renderer.setCollection("day-summary", daySummaryItems);

		return renderer.render();
	}

	private class DaySummaryItem implements Renderable {

		private String message;
		private String link = "#";
		private String icon = "";
		private String badgeType = "default";
		private String badgeText = null;

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
