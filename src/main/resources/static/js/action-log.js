$(document).ready(function () {
	setUpDataTable('#action-logs-table', [[1, 1], [1, 1], [1, 1], [1, 1], [0, 1]], {
		order: [1, 'desc'],
		processing: true,
		serverSide: true,
		ajax: '/ajax/action-log-data'
	});
});