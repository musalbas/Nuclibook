package nuclibook.routes;

import nuclibook.constants.RequestType;
import spark.Route;

public abstract class DefaultRoute implements Route {

	private RequestType requestType = RequestType.GET;

	public DefaultRoute() {

	}

	public DefaultRoute(RequestType requestType) {
		this.requestType = requestType;
	}

	public RequestType getRequestType() {
		return requestType;
	}
}
