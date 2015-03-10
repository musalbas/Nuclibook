package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TherapyUtils;
import nuclibook.entity_utils.TracerUtils;
import nuclibook.models.CameraType;
import nuclibook.models.Therapy;
import nuclibook.models.Tracer;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class CalendarRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_APPOINTMENTS, response)) return null;
        String startDate = request.queryParams("start");

		return  "{\"week\" :[ { \"day\": \"2015-03-10\", \"bookings\": [ { \"patientId\": \"0000000\", \"therapyName\": \"therapy A\", \"bookingSections\": [ { \"startTime\": \"09:30\", \"endTime\": \"11:50\" }, { \"startTime\": \"14:00\", \"endTime\": \"16:55\" } ]}, { \"patientId\": \"8888888\", \"therapyName\": \"therapy B\", \"bookingSections\": [ { \"startTime\": \"17:30\", \"endTime\": \"19:30\" }, { \"startTime\": \"20:00\", \"endTime\": \"21:00\" }, { \"startTime\": \"21:00\", \"endTime\": \"21:30\" } ]} ]}, { \"day\": \"2015-03-13\", \"bookings\": [ { \"patientId\": \"0000000\", \"therapyName\": \"therapy A\", \"bookingSections\": [ { \"startTime\": \"09:30\", \"endTime\": \"11:00\" }, { \"startTime\": \"12:00\", \"endTime\": \"12:30\" } ]}, { \"patientId\": \"8888888\", \"therapyName\": \"therapy B\", \"bookingSections\": [ { \"startTime\": \"13:30\", \"endTime\": \"17:40\" }, { \"startTime\": \"17:50\", \"endTime\": \"18:50\" }, { \"startTime\": \"19:00\", \"endTime\": \"21:20\" } ]} ]} ]}";
	}
}
