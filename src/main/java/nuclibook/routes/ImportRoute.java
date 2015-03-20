package nuclibook.routes;

import javafx.util.Pair;
import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.PatientUtils;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.*;
import spark.Request;
import spark.Response;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;

public class ImportRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		prepareToHandle(request);

		// get current user
		Staff user = SecurityUtils.getCurrentUser(request.session());

		// get request info
		String table = request.queryParams("table");
		String csvData = request.queryParams("csv-data");

		if (table.equals("patients")) {
			if (user == null || !user.hasPermission(P.IMPORT_PATIENTS)) {
				ActionLogger.logAction(user, ActionLogger.ATTEMPT_IMPORT_PATIENTS, 0, "Failed as user does not have permissions for this action");
				return "no_permission";
			}

			Integer[] importResult = new Integer[]{0, 0};
			try {
				 importResult = PatientUtils.importPatientsCSV(csvData);
			} catch (Exception e) {
				return "failed_validation";
			}

			ActionLogger.logAction(user, ActionLogger.IMPORT_PATIENTS, 0);

			return "OKAY:" + importResult[0].toString() + " rows successfully imported; " + importResult[1].toString() + " failed to import.";
		}

		// fail safe
		return "error";
	}
}
