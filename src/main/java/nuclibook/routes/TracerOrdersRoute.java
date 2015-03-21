package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.entity_utils.TracerOrderUtils;
import nuclibook.models.Staff;
import nuclibook.models.TracerOrder;
import nuclibook.server.HtmlRenderer;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * The class redirects the user to the tracer-orders.html page if he has a permission to view the page.
 */
public class TracerOrdersRoute extends DefaultRoute {
    /**
     * Method handles user's request to access tracer-orders.html page.
     *
     * @param request  Information sent by the client
     * @param response Information sent to the client
     * @return The rendered template of the tracer-orders.html page
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
            ActionLogger.logAction(user, ActionLogger.ATTEMPT_VIEW_TRACER_ORDERS, 0, "Failed as user does not have permissions for this action");
            return null;
        }

        // mode?
        String mode = request.queryParams("mode");
        ArrayList<String> modes = new ArrayList<String>() {{
            add("show-all");
            add("pending-only");
            add("order-today");
            add("order-tomorrow");
        }};
        if (mode == null || !modes.contains(mode)) {
            mode = "pending-only";
        }

        // start renderer
        HtmlRenderer renderer = getRenderer();
        renderer.setTemplateFile("tracer-orders.html");

        // get tracers and add to renderer
        List<TracerOrder> allTracerOrders = null;
        switch (mode) {
            case "show-all":
                allTracerOrders = TracerOrderUtils.getAllTracerOrders(false);
                break;

            case "pending-only":
                allTracerOrders = TracerOrderUtils.getAllTracerOrders(true);
                break;

            case "order-today":
                allTracerOrders = TracerOrderUtils.getTracerOrdersRequiredByDay(new DateTime());
                break;

            case "order-tomorrow":
                allTracerOrders = TracerOrderUtils.getTracerOrdersRequiredByDay((new DateTime()).plusDays(1));
                break;

        }
        renderer.setCollection("tracer-orders", allTracerOrders);

        // mode field
        renderer.setField("mode", mode);

        ActionLogger.logAction(user, ActionLogger.VIEW_TRACER_ORDERS, 0);

        return renderer.render();
    }
}
