package nuclibook.routes;

import nuclibook.constants.P;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

public class AjaxPatientDataRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_PATIENT_LIST, response)) {
			ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_PATIENTS, 0, "Failed as user does not have permissions for this action");
			return null;
		}

		// log
		ActionLogger.logAction(ActionLogger.VIEW_PATIENTS, 0);

		// info loaded
		int totalRecords = 1;
		ArrayList<String[]> records = new ArrayList<>();

		// build button string
		String buttonString = "";
		if (SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
			buttonString += "<button class=\"btn edit-button\" data-id=\"#id\"><i class=\"fa fa-edit\"></i> Edit</button>";
		}
		if (SecurityUtils.getCurrentUser().hasPermission(P.VIEW_PATIENT_DETAILS)) {
			buttonString += (buttonString.length() == 0 ? "" : "&nbsp;") + "<button class=\"btn info-button link-button\" data-target=\"/patient-details/#id\"><i class=\"fa fa-list-alt\"></i> View Details</button>";
		}

		// dummy data
		records.add(new String[]{"1", "2", "3", "4", "5", buttonString.replace("#id", "1")});
		records.add(new String[]{"1", "2", "3", "4", "5", buttonString.replace("#id", "1")});
		records.add(new String[]{"1", "2", "3", "4", "5", buttonString.replace("#id", "1")});
		records.add(new String[]{"1", "2", "3", "4", "5", buttonString.replace("#id", "1")});

		// output
		StringBuilder output = new StringBuilder();
		output.append("{\"recordsTotal\":").append(totalRecords).append(",\"recordsFiltered\":").append(records.size()).append(",\"data\":[");
		boolean commaNeeded = false, innerCommaNeeded;
		for (String[] r : records) {
			if (commaNeeded) output.append(",");
			commaNeeded = true;

			output.append("[");
			innerCommaNeeded = false;
			for (String s : r) {
				if (innerCommaNeeded) output.append(",");
				innerCommaNeeded = true;

				output.append("\"").append(s.replace("\"", "\\\"").replace("/", "\\/")).append("\"");
			}
			output.append("]");
		}
		output.append("]}");

		return output.toString();
	}
}
