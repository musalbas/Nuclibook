$(document).ready(function () {
	setUpDataTable('#action-logs-table', [[1, 1], [1, 1], [1, 1], [1, 1], [0, 1]], {
		order: [1, 'desc'],
		processing: true,
		serverSide: true,
		ajax: '/ajax/action-log-data'
	});
});

var onTableReloadFinished = function() {
	$('tr').each(function(i, o) {
		o = $(o);
		if (o.find('.is-error').length > 0) {
			o.addClass('error');
		} else {
			o.removeClass('error');
		}
	});
};