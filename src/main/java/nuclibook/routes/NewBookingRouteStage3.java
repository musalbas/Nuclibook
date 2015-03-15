package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.SecurityUtils;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Map;

public class NewBookingRouteStage3 extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.EDIT_APPOINTMENTS, response)) return null;

		// temp output
		JSONObject temp = new JSONObject();
		Map<String, String[]> params = request.queryMap().toMap();
		for (Map.Entry<String, String[]> e : params.entrySet()) {
			temp.put(e.getKey(), e.getValue()[0]);
		}

		return temp.toString();
	}
}
