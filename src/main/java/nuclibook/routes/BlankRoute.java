package nuclibook.routes;

import nuclibook.server.SqlServerConnection;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Connection;

public class BlankRoute implements Route {

	/**
	 * This route provides a simple "debugging" page for development use.
	 * It will display whatever message is passed in the constructor, and
	 * some diagnostic information.
	 */

	private String label = null;

	public BlankRoute() {
	}

	public BlankRoute(String label) {
		this.label = label;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// carry out connection test
		Connection connection = SqlServerConnection.acquireConnection();

		// return HTML
		return "<html>" +
				"<head>" +
				"<link type=\"text/css\" href=\"/css/blank-route-style.css\" rel=\"stylesheet\" />" +
				"</head>" +
				"<body>" +
				"<h1>Blank route" + (label == null ? "" : ": " + label) + "</h1>" +
				"<p>Connection test: " + (connection == null ? "nope =/" : "awesome! :D") + "</p>" +
				"</body>" +
				"</html>";
	}
}
