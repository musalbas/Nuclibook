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
		prepareToHandle();

		// get request info
		String table = request.queryParams("table");
		String csvData = request.queryParams("csv-data");

		if (table.equals("patients")) {
			if (SecurityUtils.getCurrentUser() == null || !SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
				return "no_permission";
			}

			Integer[] importResult = new Integer[]{0, 0};
			try {
				 importResult = PatientUtils.importPatientsCSV(csvData);
			} catch (Exception e) {
				return "failed_validation";
			}

			return "OKAY:" + importResult[0].toString() + " rows successfully imported; " + importResult[1].toString() + " failed to import.";
		}

		// fail safe
		return "error";
	}
}
