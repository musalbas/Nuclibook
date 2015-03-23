package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TracerUtils;
import nuclibook.models.Staff;
import nuclibook.models.Tracer;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * The class presents the rendered template of the tracers.html page with data on it to the user if he has a permission to view the page.
 */
public class TracersRoute extends DefaultRoute {
    /**
     * Handles user's request to view tracers.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the tracers.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.VIEW_TRACERS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_TRACERS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

        // start renderer
        HtmlRenderer renderer = getRenderer();
        renderer.setTemplateFile("tracers.html");

        // get tracers and add to renderer
        List<Tracer> allTracers = TracerUtils.getAllTracers(true);
        renderer.setCollection("tracers", allTracers);

        ActionLogger.logAction(user, ActionLogger.VIEW_TRACERS, 0);

        return renderer.render();
    }
}
