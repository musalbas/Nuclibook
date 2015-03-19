$(document).ready(function () {
	prepareDateSelector();
	setUpDataTable('#booking-history-table', [[1, 1], [1, 1], [1, 1], [1, 1], [0, 0]], {
		order: [0, 'desc']
	});
});