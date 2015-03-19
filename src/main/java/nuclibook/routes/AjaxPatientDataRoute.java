package nuclibook.routes;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import nuclibook.constants.P;
import nuclibook.entity_utils.AbstractEntityUtils;
import nuclibook.entity_utils.ActionLogger;
import nuclibook.entity_utils.SecurityUtils;
import nuclibook.models.Patient;
import nuclibook.models.Staff;
import nuclibook.server.HtmlRenderer;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class AjaxPatientDataRoute extends DefaultRoute {

	int mode;

	public AjaxPatientDataRoute(int mode) {
		this.mode = mode;
	}

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

		// get request info
		int start = Integer.parseInt(request.queryParams("start"));
		int length = Integer.parseInt(request.queryParams("length"));
		String search = "%" + request.queryParams("search[value]").toLowerCase() + "%";
		int orderCol = Integer.parseInt(request.queryParams("order[0][column]"));
		String orderDir = request.queryParams("order[0][dir]");

		// prepare query string
		String whereQuery = "LOWER(`name`) LIKE ? OR LOWER(`hospital_number`) LIKE ? OR LOWER(`nhs_number`) LIKE ? OR LOWER(`sex`) LIKE ? OR FROM_UNIXTIME(ROUND(`date_of_birth` / 1000), '%Y-%m-%d') LIKE ?";

		// prepare order string
		String orderQuery = "ORDER BY ";
		switch (orderCol) {
			case 1:
				orderQuery += "`hospital_number`";
				break;

			case 2:
				orderQuery += "`nhs_number`";
				break;

			case 3:
				orderQuery += "`sex`";
				break;

			case 4:
				orderQuery += "`date_of_birth`";
				break;

			default:
				orderQuery += "`name`";
				break;
		}
		if (orderDir.equals("asc")) {
			orderQuery += " ASC";
		} else {
			orderQuery += " DESC";
		}

		// get patient DAO
		Dao<Patient, Integer> dao = AbstractEntityUtils.acquireDao(Patient.class);

		// query to get ALL results
		GenericRawResults<String[]> rawTotalResults = dao.queryRaw("SELECT COUNT(*) FROM `patients`");
		List<String[]> totalResults = rawTotalResults.getResults();
		int totalRecords = Integer.parseInt((totalResults.get(0))[0]);

		// query to get ALL filtered results
		rawTotalResults = dao.queryRaw("SELECT COUNT(*) FROM `patients` WHERE " + whereQuery, search, search, search, search, search);
		totalResults = rawTotalResults.getResults();
		int totalFilteredRecords = Integer.parseInt((totalResults.get(0))[0]);

		// query for matched rows
		ArrayList<String[]> records = new ArrayList<>();
		GenericRawResults<Patient> rawResults = dao.queryRaw("SELECT * FROM `patients` WHERE " + whereQuery + " " + orderQuery + " LIMIT " + start + ", " + length, dao.getRawRowMapper(), search, search, search, search, search);
		List<Patient> results = rawResults.getResults();

		// build button string (do this just once for efficiency)
		String buttonString = "";
		if (mode == 0) {
			if (SecurityUtils.getCurrentUser(request.session()).hasPermission(P.EDIT_PATIENTS)) {
				buttonString += "<button class=\"btn edit-button\" data-id=\"#id\"><i class=\"fa fa-edit\"></i> Edit</button>";
			}
			if (SecurityUtils.getCurrentUser(request.session()).hasPermission(P.VIEW_PATIENT_DETAILS)) {
				buttonString += (buttonString.length() == 0 ? "" : "&nbsp;") + "<button class=\"btn info-button link-button\" data-target=\"/patient-details/#id\"><i class=\"fa fa-list-alt\"></i> View Details</button>";
			}
		} else {
			buttonString = "<button class=\"btn select-patient confirm-button\" data-id=\"#id\" data-name=\"#name\"><i class=\"fa fa-fw fa-arrow-circle-right\"></i> Select</button>";
		}

		// create rows
		Patient p;
		for (int i = 0; i < results.size(); ++i) {
			p = results.get(i);
			records.add(new String[]{
					p.getName(),
					p.getHospitalNumber(),
					p.getNhsNumber(),
					p.getSex() == Patient.Sex.MALE ? "M" : "F",
					p.getDateOfBirth().toString("YYYY-MM-dd"),
					buttonString.replace("#id", p.getId().toString()).replace("#name", p.getName()) +
							(i == results.size() - 1 ? HtmlRenderer.getCollectionMapHtml(results, "objectMap") : "")
			});
		}

		// output
		StringBuilder output = new StringBuilder();
		output.append("{\"recordsTotal\":").append(totalRecords).append(",\"recordsFiltered\":").append(totalFilteredRecords).append(",\"data\":[");
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
