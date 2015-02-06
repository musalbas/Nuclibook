package nuclibook.routes;

import spark.Request;
import spark.Response;
import spark.Route;

public class BlankRoute implements Route {

	private String label = null;

	public BlankRoute() {
	}

	public BlankRoute(String label) {
		this.label = label;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return "<html>" +
				"<head>" +
				"<link type=\"text/css\" href=\"/css/blank-route-style.css\" rel=\"stylesheet\" />" +
				"</head>" +
				"<body>" +
				"<p>Blank route" + (label == null ? "" : ": " + label) + "</p>" +
				"</body>" +
				"</html>";
	}
}
