package nuclibook.page_routes;

import spark.Request;
import spark.Response;
import spark.Route;

public class DummyPageRoute implements Route {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return "This is a test route.";
	}
}
