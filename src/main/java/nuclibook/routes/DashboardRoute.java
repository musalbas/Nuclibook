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

/**
 * The class presents the rendered template of the dashboard.html page with data on it to the user.
 */
public class DashboardRoute extends DefaultRoute {
    /**
     * Handles user's request to view dashboard.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the dashboard.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        prepareToHandle(request);

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
            dsiBookingsToday.setLink("/bookings?date=" + today.toString("YYYY-MM-dd"));
            dsiBookingsToday.setBadgeText("click to view");
        } else {
            dsiBookingsToday = new DaySummaryItem("There are <strong>" + bookingsToday.size() + " bookings</strong> today");
            dsiBookingsToday.setLink("/bookings?date=" + today.toString("YYYY-MM-dd"));
            dsiBookingsToday.setBadgeText("click to view");
        }
        dsiBookingsToday.setIcon("fa-clock-o");
        daySummaryItems.add(dsiBookingsToday);

        // day summary: bookings tomorrow
        List<Booking> bookingsTomorrow = BookingUtils.getBookingsByDateRange(tomorrowStart, tomorrowEnd);
        DaySummaryItem dsiBookingsTomorrow;
        if (bookingsTomorrow == null || bookingsTomorrow.size() == 0) {
            dsiBookingsTomorrow = new DaySummaryItem("There are <strong>no bookings</strong> tomorrow");
        } else if (bookingsTomorrow.size() == 1) {
            dsiBookingsTomorrow = new DaySummaryItem("There is <strong>1 booking</strong> tomorrow");
            dsiBookingsTomorrow.setLink("/bookings?date=" + tomorrow.toString("YYYY-MM-dd"));
            dsiBookingsTomorrow.setBadgeText("click to view");
        } else {
            dsiBookingsTomorrow = new DaySummaryItem("There are <strong>" + bookingsTomorrow.size() + " bookings</strong> tomorrow");
            dsiBookingsTomorrow.setLink("/bookings?date=" + tomorrow.toString("YYYY-MM-dd"));
            dsiBookingsTomorrow.setBadgeText("click to view");
        }
        dsiBookingsTomorrow.setIcon("fa-clock-o");
        daySummaryItems.add(dsiBookingsTomorrow);

        // day summary: tracer orders today
        List<TracerOrder> tracerOrdersRequiredToday = TracerOrderUtils.getTracerOrdersRequiredByDay(today, true);
        DaySummaryItem dsiTracerOrdersToday;
        if (tracerOrdersRequiredToday == null || tracerOrdersRequiredToday.size() == 0) {
            dsiTracerOrdersToday = new DaySummaryItem("There are <strong>no tracers</strong> to be ordered today");
        } else if (tracerOrdersRequiredToday.size() == 1) {
            dsiTracerOrdersToday = new DaySummaryItem("There is <strong>1 tracer</strong> to be ordered today");
            dsiTracerOrdersToday.setBadgeText("click to view");
        } else {
            dsiTracerOrdersToday = new DaySummaryItem("There are <strong>" + tracerOrdersRequiredToday.size() + " tracers</strong> to be ordered today");
            dsiTracerOrdersToday.setBadgeText("click to view");
        }
        dsiTracerOrdersToday.setLink("/tracer-orders?mode=order-today");
        dsiTracerOrdersToday.setIcon("fa-flask");
        daySummaryItems.add(dsiTracerOrdersToday);

        // day summary: tracer orders tomorrow
        List<TracerOrder> tracerOrdersRequiredTomorrow = TracerOrderUtils.getTracerOrdersRequiredByDay(tomorrow, true);
        DaySummaryItem dsiTracerOrdersTomorrow;
        if (tracerOrdersRequiredTomorrow == null || tracerOrdersRequiredTomorrow.size() == 0) {
            dsiTracerOrdersTomorrow = new DaySummaryItem("There are <strong>no tracers</strong> to be ordered tomorrow");
        } else if (tracerOrdersRequiredTomorrow.size() == 1) {
            dsiTracerOrdersTomorrow = new DaySummaryItem("There is <strong>1 tracer</strong> to be ordered tomorrow");
            dsiTracerOrdersTomorrow.setBadgeText("click to view");
        } else {
            dsiTracerOrdersTomorrow = new DaySummaryItem("There are <strong>" + tracerOrdersRequiredTomorrow.size() + " tracers</strong> to be ordered tomorrow");
            dsiTracerOrdersTomorrow.setBadgeText("click to view");
        }
        dsiTracerOrdersTomorrow.setLink("/tracer-orders?mode=order-tomorrow");
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

    /**
     * Inner class to represents data on the day summary.
     */
    private class DaySummaryItem implements Renderable {

        private String message;
        private String link = "javascript:;";
        private String icon = "";
        private String badgeType = "default";
        private String badgeText = null;

        /**
         * Default constructor
         */
        public DaySummaryItem() {
        }

        /**
         * Creates day summary item with the specified message.
         * @param message The message to be used
         */
        public DaySummaryItem(String message) {
            this.message = message;
        }

        /**
         * Gets the message.
         * @return The message used.
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the message.
         * @param message The message to be used
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Gets the link.
         * @return The link used
         */
        public String getLink() {
            return link;
        }

        /**
         * Sets the link to be used.
         * @param link The link to be used
         */
        public void setLink(String link) {
            this.link = link;
        }

        /**
         * Gets the icon.
         * @return The icon used
         */
        public String getIcon() {
            return icon;
        }

        /**
         * Sets the icon.
         * @param icon The icon to be used
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }

        /**
         * Gets the badge type.
         * @return the badge type used.
         */
        public String getBadgeType() {
            return badgeType;
        }

        /**
         * Sets the badge type.
         * @param badgeType The badge type to be used
         */
        public void setBadgeType(String badgeType) {
            this.badgeType = badgeType;
        }

        /**
         *Gets the text displayed on the badge.
         * @return text displayed on the badge
         */
        public String getBadgeText() {
            return badgeText;
        }

        /**
         * Sets the text for the badge.
         * @param badgeText The text to be used on the badge
         */
        public void setBadgeText(String badgeText) {
            this.badgeText = badgeText;
        }

        /**
         * Gets the hashmap of the items used in the day summary.
         * @return HashMap of items used in the day summary
         */
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
