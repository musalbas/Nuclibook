package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;
/**
 * The class redirects the user to the patients.html page if he has a permission to view the page.
 */
public class PatientsRoute extends DefaultRoute {
    /**
     * method handles user's request to view patients.html page.
     *
     * @param request  Information sent by the client.
     * @param response Information sent to the client.
     * @return The rendered template of the patients.html page.
     * @throws Exception if something goes wrong, for example, loss of connection with a server.
     */
	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// security check
		if (!SecurityUtils.requirePermission(user, P.VIEW_PATIENT_LIST, response)) {
			ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_PATIENTS, 0, "Failed as user does not have permissions for this action");
			return null;
		}

		// start renderer
		HtmlRenderer renderer = getRenderer();
		renderer.setTemplateFile("patients.html");

		ActionLogger.logAction(user, ActionLogger.VIEW_PATIENTS, 0);

		return renderer.render();
	}
}
