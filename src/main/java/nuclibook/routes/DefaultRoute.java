package nuclibook.routes;

import nuclibook.constants.C;
import nuclibook.constants.RequestType;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Route;
import spark.Session;

/**
 * Abstract class which is extended by all other routes classes in the application.
 */
public abstract class DefaultRoute implements Route {

    private RequestType requestType;
    private HtmlRenderer renderer;

    public DefaultRoute() {
        this(RequestType.GET);
    }

    public DefaultRoute(RequestType requestType) {
        // set request type
        this.requestType = requestType;

        // set up renderer
        renderer = new HtmlRenderer();
    }

    /**
     * performs the routines common to all the routes in the application.
     * These routines are necessary to perform before handling each user's request.
     *
     * @param request Information sent by the client.
     */
    public void prepareToHandle(Request request) {
        // make sure this is a fresh start
        renderer.clearFields();
        renderer.clearCollections();

        // get current session and user
        Session session = request.session();
        Staff user = SecurityUtils.getCurrentUser(session);

        // csrf token
        renderer.setField("csrf-token", SecurityUtils.getCsrfToken(session));

        // set up login field
        if (SecurityUtils.checkLoggedIn(session)) {
            renderer.setField("logged-in", "yes");

            // automatic logout timer
            renderer.setField("automatic-timeout", C.AUTOMATIC_TIMEOUT);

            // set user
            renderer.setCurrentUser(user);

            // add staff details;
            renderer.setField("current-user-id", user.getId());
            renderer.setField("current-user-username", user.getUsername());
            renderer.setField("current-user-name", user.getName());
            renderer.setField("current-user-role", user.getRole().getLabel());
            renderer.setField("current-user-permissions-summary", user.getRole().getPermissionSummary());
            renderer.setField("current-user-days-until-password-change", user.getDaysRemainingToPasswordChange());

            // do they need a password change reminder?
            if (user.getDaysRemainingToPasswordChange() >= 1 && user.getDaysRemainingToPasswordChange() <= 7) {
                renderer.setField("show-password-reminder", "yes");
            }
        } else {
            renderer.setField("logged-in", "no");

            // automatic logout timer
            renderer.setField("automatic-timeout", 0);
        }
    }

    /**
     * Gets the request type.
     *
     * @return RequestType : GET or POST.
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * Gets the renderer.
     *
     * @return HtmlRenderer.
     */
    public HtmlRenderer getRenderer() {
        return renderer;
    }
}
