package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.GenericEvent;
import nuclibook.models.Staff;
import nuclibook.models.StaffAbsence;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

public class GenericEventsRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_GENERIC_EVENTS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_GENERIC_EVENTS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("generic-events.html");

		// add generic events to renderer
		List<GenericEvent> allGenericEvents = GenericEventUtils.getAllGenericEvents();
		renderer.setCollection("generic-events", allGenericEvents);

        ActionLogger.logAction(user, ActionLogger.VIEW_GENERIC_EVENTS, 0);

		return renderer.render();
	}
}
