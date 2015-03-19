package nuclibook.routes;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import org.joda.time.DateTime;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

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

		// get request info
		int start = Integer.parseInt(request.queryParams("start"));
		int length = Integer.parseInt(request.queryParams("length"));
		String search = "%" + request.queryParams("search[value]").toLowerCase() + "%";
		int orderCol = Integer.parseInt(request.queryParams("order[0][column]"));
		String orderDir = request.queryParams("order[0][dir]");

		// prepare query string
		String whereQuery = "LOWER(`name`) LIKE ? OR LOWER(`hospital_number`) LIKE ? OR LOWER(`nhs_number`) LIKE ? OR LOWER(`sex`) LIKE ? OR LOWER(`date_of_birth`) LIKE ?";

		// get patient DAO
		Dao<Patient, Integer> dao = AbstractEntityUtils.acquireDao(Patient.class);

		// query to get ALL results
		GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT COUNT(*) FROM `patients` WHERE " + whereQuery, search, search, search, search, search);
		List<String[]> results = rawResults.getResults();
		int totalRecords = Integer.parseInt((results.get(0))[0]);

		// query for matched rows
		ArrayList<String[]> records = new ArrayList<>();
		rawResults = dao.queryRaw("SELECT * FROM `patients` WHERE " + whereQuery, search, search, search, search, search);
		results = rawResults.getResults();

		// build button string (do this just once for efficiency)
		String buttonString = "";
		if (SecurityUtils.getCurrentUser().hasPermission(P.EDIT_PATIENTS)) {
			buttonString += "<button class=\"btn edit-button\" data-id=\"#id\"><i class=\"fa fa-edit\"></i> Edit</button>";
		}
		if (SecurityUtils.getCurrentUser().hasPermission(P.VIEW_PATIENT_DETAILS)) {
			buttonString += (buttonString.length() == 0 ? "" : "&nbsp;") + "<button class=\"btn info-button link-button\" data-target=\"/patient-details/#id\"><i class=\"fa fa-list-alt\"></i> View Details</button>";
		}

		// create rows
		for (String[] row : results) {
			records.add(new String[]{
					row[1],
					row[2],
					row[3],
					row[5].equals("MALE") ? "M" : "F",
					new DateTime(Long.parseLong(row[4])).toString("YYYY-MM-dd"),
					buttonString.replace("#id", row[0])
			});
		}

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
