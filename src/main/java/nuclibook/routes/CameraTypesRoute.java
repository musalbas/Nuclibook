package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.CameraTypeUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.CameraType;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.List;

/**
 * The class redirects the user to the camera-types.html page if he has a permission to view the page.
 */
public class CameraTypesRoute extends DefaultRoute {
    /**
     * Method handles user's request to view cameras-types.html page.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the camera-types.html page
     * @throws Exception if something goes wrong, for example, loss of connection with a server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // necessary prelim routine
        prepareToHandle(request);

        // get current user
        Staff user = SecurityUtils.getCurrentUser(request.session());

        // security check
        if (!SecurityUtils.requirePermission(user, P.VIEW_CAMERAS, response)) {
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_CAMERA_TYPES, 0, "Failed as user does not have permissions for this action");
            return null;
        }

        // start renderer
        HtmlRenderer renderer = getRenderer();
        renderer.setTemplateFile("camera-types.html");

        // get camera types and add to renderer
        List<CameraType> allCameraTypes = CameraTypeUtils.getAllCameraTypes(true);
        renderer.setCollection("camera-types", allCameraTypes);

        ActionLogger.logAction(user, ActionLogger.VIEW_CAMERA_TYPES, 0);

        return renderer.render();
    }
}
