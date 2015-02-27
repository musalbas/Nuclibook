function setUpDataTable(tableId, orderBy, colDefs) {

	// make column defs
	var columnDefs = [];
	for (var c in colDefs) {
		columnDefs[columnDefs.length] = {
			"orderable": colDefs[c][0] > 0,
			"searchable": colDefs[c][1] > 0
		};
	}

	// do the magic
	$(tableId).DataTable({
		"language": {
			"aria": {
				"sortAscending": ": activate to sort column ascending",
				"sortDescending": ": activate to sort column descending"
			},
			"emptyTable": "No data available in table",
			"info": "Showing _START_ to _END_ of _TOTAL_ entries",
			"infoEmpty": "No entries found",
			"infoFiltered": "(filtered from _MAX_ total entries)",
			"lengthMenu": "Show _MENU_ entries",
			"search": "Search:",
			"zeroRecords": "No matching records found.",
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
		"pagingType": "bootstrap_full_number",
		"order": [
			[orderBy, "asc"]
		]
	});

}