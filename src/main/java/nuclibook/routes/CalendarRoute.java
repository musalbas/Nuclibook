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

		return "{ \"week\": [" +
                        " { \"day\": \"12-03-15\", " +
                        " \"bookings\": [ " +
                                        " { \"patient-id\": \"0000000\", " +
                                        " \"therapy-name\": \"therapy A\", " +
                                        " \"booking-sections\": [ " +
                                                                " { \"start-time\": \"15:30\", " +
                                                                "   \"end-time\": \"15:50\" }, " +

                                                                " { \"start-time\": \"16:00\", " +
                                                                "   \"end-time\": \"16:55\" } " +
                                                                "]" +
                                        "}, " +

                                        " { \"patient-id\": \"8888888\", " +
                                                " \"therapy-name\": \"therapy B\", " +
                                                " \"booking-sections\": [ " +
                                                                        " { \"start-time\": \"17:30\", " +
                                                                        "   \"end-time\": \"17:40\" }, " +

                                                                        " { \"start-time\": \"17:50\", " +
                                                                        "   \"end-time\": \"18:00\" }, " +

                                                                        " { \"start-time\": \"18:10\", " +
                                                                        "   \"end-time\": \"18:20\" } " +
                                                                        "]" +
                                        "} " +

                                        "]" +
                    "}, " +
                    " { \"day\": \"12-03-15\", " +
                    " \"bookings\": [ " +
                                    " { \"patient-id\": \"0000000\", " +
                                    " \"therapy-name\": \"therapy A\", " +
                                    " \"booking-sections\": [ " +
                                                            " { \"start-time\": \"15:30\", " +
                                                            "   \"end-time\": \"15:50\" }, " +

                                                            " { \"start-time\": \"16:00\", " +
                                                            "   \"end-time\": \"16:55\" } " +
                                                             "]" +
                                    "}, " +

                                    " { \"patient-id\": \"8888888\", " +
                                    " \"therapy-name\": \"therapy B\", " +
                                    " \"booking-sections\": [ " +
                                                            " { \"start-time\": \"17:30\", " +
                                                            "   \"end-time\": \"17:40\" }, " +

                                                            " { \"start-time\": \"17:50\", " +
                                                            "   \"end-time\": \"18:00\" }, " +

                                                            " { \"start-time\": \"18:10\", " +
                                                            "   \"end-time\": \"18:20\" } " +
                                                            "]" +
                                     "} " +

                                    "]" +
                    "} " +
                    "]" +
                "}";
	}
}
