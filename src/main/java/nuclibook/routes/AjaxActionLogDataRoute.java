package nuclibook.routes;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import nuclibook.constants.P;
import nuclibook.entity_utils.*;
import nuclibook.models.ActionLog;
import nuclibook.models.ActionLogEvent;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class AjaxActionLogDataRoute extends DefaultRoute {

	@Override
	public Object handle(Request request, Response response) throws Exception {
		// necessary prelim routine
		prepareToHandle();

		// security check
		if (!SecurityUtils.requirePermission(P.VIEW_ACTION_LOG, response)) {
			ActionLogger.logAction(ActionLogger.ATTEMPT_VIEW_ACTION_LOG, 0, "Failed as user does not have permissions for this action");
			return null;
		}

		// get request info
		int start = Integer.parseInt(request.queryParams("start"));
		int length = Integer.parseInt(request.queryParams("length"));
		String search = "%" + request.queryParams("search[value]").toLowerCase() + "%";
		int orderCol = Integer.parseInt(request.queryParams("order[0][column]"));
		String orderDir = request.queryParams("order[0][dir]");

		// prepare query string
		String whereQuery = "LOWER(`staff`.`name`) LIKE ? OR (" + (search.equals("") ? "`action_log_events`.`label` IS NULL OR " : "") + "LOWER(`action_log_events`.`label`) LIKE ?) OR FROM_UNIXTIME(ROUND(`when` / 1000), '%Y-%m-%d') LIKE ? OR `associated_id` LIKE ? OR (" + (search.equals("") ? "`note` IS NULL OR " : "") + "LOWER(`note`) LIKE ?)";

		// prepare order string
		String orderQuery = "ORDER BY ";
		switch (orderCol) {
			// TODO

			default:
				orderQuery += "`when`";
				break;
		}
		if (orderDir.equals("asc")) {
			orderQuery += " ASC";
		} else {
			orderQuery += " DESC";
		}

		// get action DAO
		Dao<ActionLog, Integer> dao = AbstractEntityUtils.acquireDao(ActionLog.class);

		// query to get ALL results
		GenericRawResults<String[]> rawTotalResults = dao.queryRaw("SELECT COUNT(*) FROM `action_log`");
		List<String[]> totalResults = rawTotalResults.getResults();
		int totalRecords = Integer.parseInt((totalResults.get(0))[0]);

		// query to get ALL filtered results
		rawTotalResults = dao.queryRaw("SELECT COUNT(*) FROM (`action_log` LEFT OUTER JOIN `staff` ON `action_log`.`staff_id` = `staff`.`id`) LEFT OUTER JOIN `action_log_events` ON `action_log`.`action_id` = `action_log_events`.`id` WHERE " + whereQuery, search, search, search, search, search);
		totalResults = rawTotalResults.getResults();
		int totalFilteredRecords = Integer.parseInt((totalResults.get(0))[0]);

		// query for matched rows
		ArrayList<String[]> records = new ArrayList<>();
		GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT * FROM (`action_log` LEFT OUTER JOIN `staff` ON `action_log`.`staff_id` = `staff`.`id`) LEFT OUTER JOIN `action_log_events` ON `action_log`.`action_id` = `action_log_events`.`id` WHERE " + whereQuery + " " + orderQuery + " LIMIT " + start + ", " + length, search, search, search, search, search);
		List<String[]> results = rawResults.getResults();

		// create rows
		ActionLog a;
		ActionLogEvent e;
		for (String[] row : results) {
			a = ActionLogUtils.getActionLog(row[0]);
			e = ActionLogEventUtils.getActionLogEvent(a.getActionId());
			records.add(new String[]{
					a.getStaff() == null ?
							"Unknown" :
							a.getStaff().getName(),
					a.getWhen().toString("YYYY-MM-dd HH:mm:ss"),
					e == null ?
							"Unknown" :
							e.getLabel(),
					(a.getAssociatedId() == 0 ?
							"-" :
							a.getAssociatedId().toString()),
					(a.getNote() == null ?
							"-" :
							a.getNote()) +
							(e != null && e.isError() ?
									"<span class=\"is-error\"></span>" :
									"")
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
