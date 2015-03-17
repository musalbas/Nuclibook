package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.*;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.net.URLDecoder;
import java.util.ArrayList;

public class NewBookingRouteStage3 extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.EDIT_APPOINTMENTS, response)) return null;

		// get basic info from post
		Patient patient = PatientUtils.getPatient(request.queryParams("patient"));
		Therapy therapy = TherapyUtils.getTherapy(request.queryParams("therapy"));
		Camera camera = CameraUtils.getCamera(request.queryParams("camera"));
		Tracer tracer = TracerUtils.getTracer(request.queryParams("tracer"));
		String tracerDose = request.queryParams("tracer-dose");

		// get tracer order date
		DateTime tracerOrderDate = null;
		if (request.queryParams("no-tracer-order") == null || !request.queryParams("no-tracer-order").equals("1")) {
			tracerOrderDate = new DateTime(request.queryParams("tracer-order-due"));
		}

		// get assigned staff
		ArrayList<Staff> assignedStaff = new ArrayList<>();
		String[] assignedStaffIds = request.queryParams("assigned-staff").split(",");
		for (String staffId : assignedStaffIds) {
			if (!staffId.equals("0")) {
				Staff staff = StaffUtils.getStaff(staffId);
				if (staff != null) assignedStaff.add(staff);
			}
		}

		// get booking sections
		ArrayList<BookingSection> bookingSections = new ArrayList<>();
		JSONArray bookingSectionsJsonArray = new JSONArray(URLDecoder.decode(request.queryParams("booking-sections"), "UTF-8"));
		for (int i = 0; i < bookingSectionsJsonArray.length(); ++i) {
			JSONObject tempObject = bookingSectionsJsonArray.getJSONObject(i);
			BookingSection tempBookingSection = new BookingSection(new DateTime(tempObject.getString("startTime")), new DateTime(tempObject.getString("endTime")));
			bookingSections.add(tempBookingSection);
		}

		// check the data received - should have all be validated on the front end,
		// so just check that we got everything or fail
		if (patient == null
				|| therapy == null
				|| camera == null
				|| tracer == null
				|| tracerDose == null
				|| tracerDose.length() == 0
				|| bookingSections.size() == 0) {
			response.redirect("/");
			return null;
		}

		// save booking in DB
		Booking booking = new Booking();
		booking.setPatient(patient);
		booking.setTherapy(therapy);
		booking.setCamera(camera);
		booking.setTracer(tracer);
		booking.setTracerDose(tracerDose);
		booking.setStatus("unconfirmed");
		Booking entity = AbstractEntityUtils.createEntity(Booking.class, booking);

		// add booking sections
		for (BookingSection bs : bookingSections) {
			bs.setBooking(booking);
			AbstractEntityUtils.createEntity(BookingSection.class, bs);
		}

		// add staff
		for (Staff s : assignedStaff) {
			BookingStaff bs = new BookingStaff(booking, s);
			AbstractEntityUtils.createEntity(BookingStaff.class, bs);
		}

		// create tracer order
		if (tracerOrderDate != null) {
			TracerOrder tracerOrder = new TracerOrder();
			tracerOrder.setTracer(tracer);
			tracerOrder.setTracerDose(tracerDose);
			tracerOrder.setBooking(booking);
			tracerOrder.setOrderBy(tracerOrderDate);
			tracerOrder.setDateRequired(bookingSections.get(0).getStart());
			tracerOrder.setStatus("pending");
			AbstractEntityUtils.createEntity(TracerOrder.class, tracerOrder);
		}

		// log creation
        	ActionLogger.logAction(ActionLogger.CREATE_BOOKING, entity.getId());

		// forward to booking details
		response.redirect("/booking-details/" + booking.getId());
		return null;
	}
}
