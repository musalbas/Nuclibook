package nuclibook.routes;

import spark.Request;
import spark.Response;

public class NewBookingRouteStage2 extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		return request.queryParams("sections");
	}
}
