package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.Booking;
import nuclibook.models.BookingSection;
import nuclibook.models.GenericEvent;
import nuclibook.models.StaffAbsence;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AjaxCalendarDataRoute extends DefaultRoute {

	private HashMap<Integer, Integer> cameraIdColours = new HashMap<>();

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

		// start json
		StringBuilder jsonOutput = new StringBuilder();
		jsonOutput.append("{");
		boolean commaNeeded;

		/*
		BOOKINGS SECTION
		 */

		if (request.queryParams("bookings") != null && request.queryParams("bookings").equals("1")) {
			// get bookings between start/end dates
			List<Booking> bookings = BookingUtils.getBookingsByDateRange(startDate, endDate);

			// include cancelled?
			boolean includeCancelledBookings = request.queryParams("cancelledBookings") != null && request.queryParams("cancelledBookings").equals("1");

			// filtering by camera?
			boolean filterCameras = false;
			ArrayList<Integer> allowedCameras = new ArrayList<>();
			if (request.queryParams("cameras") != null && !request.queryParams("cameras").equals("all")) {
				filterCameras = true;
				String[] rawAllowedCameras = request.queryParams("cameras").split(",");
				for (String rac : rawAllowedCameras) {
					try {
						allowedCameras.add(Integer.parseInt(rac));
					} catch (NumberFormatException nfe) {
						// meh.
					}
				}
			}

			// open section
			jsonOutput.append("\"bookings\": [");

			// loop bookings
			commaNeeded = false;
			for (Booking booking : bookings) {
				// include cancelled?
				if (!includeCancelledBookings && booking.getStatus().equals("cancelled")) {
					continue;
				}

				// filter by camera?
				if (filterCameras && !allowedCameras.contains(booking.getCamera().getId())) {
					continue;
				}

				// open booking object
				if (commaNeeded) {
					jsonOutput.append(",");
				}
				commaNeeded = true;
				jsonOutput.append("{");

				// append basic info
				jsonOutput.append("\"id\": \"").append(booking.getId()).append("\",");
				jsonOutput.append("\"patientName\": \"").append(booking.getPatient().getName()).append("\",");
				jsonOutput.append("\"therapyName\": \"").append(booking.getTherapy().getName().replace("\"", "\\\"")).append("\",");
				jsonOutput.append("\"colourNumber\": ").append(colourNumber(booking.getCamera().getId())).append(",");
				jsonOutput.append("\"cameraName\": \"")
						.append(CameraTypeUtils
										.getCameraType(
												booking
														.getCamera()
														.getType()
														.getId()
										).getLabel()
										.replace("\"", "\\\"")
						)
						.append(", ")
						.append(booking
								.getCamera()
								.getRoomNumber()
								.replace("\"", "\\\""))
						.append("\",");
				jsonOutput.append("\"status\": \"").append(booking.getStatus()).append("\",");

				// open booking section array
				jsonOutput.append("\"bookingSections\": [");

				// get and loop booking sections
				List<BookingSection> bookingSections = booking.getBookingSections();
				for (int j = 0; j < bookingSections.size(); j++) {
					// open object
					if (j != 0) jsonOutput.append(",");
					jsonOutput.append("{");

					// append times
					jsonOutput.append("\"startTime\": \"").append(bookingSections.get(j).getStart().toString("YYYY-MM-dd HH:mm")).append("\",");
					jsonOutput.append("\"endTime\": \"").append(bookingSections.get(j).getEnd().toString("YYYY-MM-dd HH:mm")).append("\"");

					// close object
					jsonOutput.append("}");
				}

				// close booking section array
				jsonOutput.append("]");

				// close booking object
				jsonOutput.append("}");
			}

			// close booking array
			jsonOutput.append("],");
		}

		/*
		END BOOKINGS SECTION
		 */

		/*
		STAFF ABSENCES SECTION
		 */

		if (request.queryParams("staffAbsences") != null && request.queryParams("staffAbsences").equals("1")) {
			// get absences between start/end dates
			List<StaffAbsence> staffAbsences = StaffAbsenceUtils.getStaffAbsencesByDateRange(startDate, endDate);

			// open section
			jsonOutput.append("\"staffAbsences\": [");

			// loop bookings
			commaNeeded = false;
			for (StaffAbsence staffAbsence : staffAbsences) {
				// open staff absence object
				if (commaNeeded) {
					jsonOutput.append(",");
				}
				commaNeeded = true;
				jsonOutput.append("{");

				// append info
				jsonOutput.append("\"id\": \"").append(staffAbsence.getId()).append("\",");
				jsonOutput.append("\"staffId\": \"").append(staffAbsence.getStaff().getId()).append("\",");
				jsonOutput.append("\"staffName\": \"").append(staffAbsence.getStaff().getName()).append("\",");
				jsonOutput.append("\"from\": \"").append(staffAbsence.getFrom().toString("YYYY-MM-dd HH:mm")).append("\",");
				jsonOutput.append("\"to\": \"").append(staffAbsence.getTo().toString("YYYY-MM-dd HH:mm")).append("\"");

				// close absence object
				jsonOutput.append("}");
			}

			// close absences array
			jsonOutput.append("],");
		}

		/*
		END STAFF ABSENCES SECTION
		 */

		/*
		GENERIC EVENTS SECTION
		 */

		if (request.queryParams("genericEvents") != null && request.queryParams("genericEvents").equals("1")) {
			// get events between start/end dates
			List<GenericEvent> genericEvents = GenericEventUtils.getGenericEventsByDateRange(startDate, endDate);

			// open section
			jsonOutput.append("\"genericEvents\": [");

			// loop bookings
			commaNeeded = false;
			for (GenericEvent genericEvent : genericEvents) {
				// open event object
				if (commaNeeded) {
					jsonOutput.append(",");
				}
				commaNeeded = true;
				jsonOutput.append("{");

				// append info
				jsonOutput.append("\"id\": \"").append(genericEvent.getId()).append("\",");
				jsonOutput.append("\"title\": \"").append(genericEvent.getTitle().replace("\"", "\\\"")).append("\",");
				jsonOutput.append("\"description\": \"").append(genericEvent.getDescription().replace("\"", "\\\"")).append("\",");
				jsonOutput.append("\"from\": \"").append(genericEvent.getFrom().toString("YYYY-MM-dd HH:mm")).append("\",");
				jsonOutput.append("\"to\": \"").append(genericEvent.getTo().toString("YYYY-MM-dd HH:mm")).append("\"");

				// close absence object
				jsonOutput.append("}");
			}

			// close absences array
			jsonOutput.append("],");
		}

		/*
		END GENERIC EVENTS SECTION
		 */

		return jsonOutput.substring(0, jsonOutput.length() == 1 ? 1 : jsonOutput.length() - 1) + "}";
	}

	private int colourNumber(int cameraId) {
		int limit = 4;
		if (cameraIdColours.containsKey(cameraId)) {
			return cameraIdColours.get(cameraId);
		} else {
			int thisColour = (cameraIdColours.size() % limit) + 1;
			cameraIdColours.put(cameraId, thisColour);
			return thisColour;
		}
	}
}
