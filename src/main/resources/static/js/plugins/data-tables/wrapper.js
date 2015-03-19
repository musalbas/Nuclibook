function mergeObject(ob1, ob2) {
	for (var p in ob2) ob1[p] = ob2[p];
	return ob1;
}

function setUpDataTable(tableId, colDefs, extraConfig) {

	// make column defs
	var columnDefs = [];
	for (var c in colDefs) {
		columnDefs[columnDefs.length] = {
			"orderable": colDefs[c][0] > 0,
			"searchable": colDefs[c][1] > 0
		};
	}

	// suppress warning
	window.alert = (function () {
		var nativeAlert = window.alert;
		return function (message) {
			window.alert = nativeAlert;
			typeof(message) == 'string' && message.indexOf("DataTables warning") === 0 ?
				console.warn(message) :
				nativeAlert(message);
		}
	})();

	// do the magic
	$(tableId)
		.DataTable(mergeObject({
			"language": {
				"aria": {
					"sortAscending": ": activate to sort column ascending",
					"sortDescending": ": activate to sort column descending"
				},
				"emptyTable": "<em>There is no data to display in this table.</em>",
				"info": "Showing _START_ of _END_ (_TOTAL_ results found)",
				"infoEmpty": "No entries found",
				"infoFiltered": "from _MAX_ entries",
				"lengthMenu": "Display _MENU_ rows",
				"search": "",
				"zeroRecords": "<em>We couldn't find what you were searching for. Please check your spelling or try searching manually.</em>",
				"paginate": {
					"previous": "Prev",
					"next": "Next",
					"last": "Last",
					"first": "First"
				}
			},
			"bStateSave": false,
			"columns": columnDefs,
			"lengthMenu": [
				[20, 50, -1],
				[20, 50, "All"]
			],
			"pageLength": 20,
			"pagingType": "bootstrap_full_number"
		}, extraConfig))
		.on('preXhr.dt', function (e, s, j) {
			$(this).fadeTo(0, 0.4);
		})
		.on('xhr.dt', function (e, s, j) {
			$(this).fadeTo(0, 1.0);
		})
		.on('draw.dt', function () {
			globalOnTableReloadFinished();
			if (typeof(onTableReloadFinished) == 'function') {
				onTableReloadFinished();
			}
		});

	// kill processing message
	$('.dataTables_processing').remove();

}
