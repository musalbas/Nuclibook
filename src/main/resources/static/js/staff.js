
var validateCreateForm = function (formObject) {
	var error = false;

    // check name to be in interval [1,64]
    if (formObject.name.trim().length < 1) {
        toastr.error("You did not enter a valid name.");
        error = true;
    }
    if (formObject.name.length > 64) {
        toastr.error("Name of the staff should not exceed 64 characters");
        error = true;
    }

    // check username to be in interval [1,64]
    if (formObject.username.trim().length < 1) {
        toastr.error("You did not enter a valid username.");
        error = true;
    }
    if (formObject.username.length > 64) {
        toastr.error("Username should not exceed 64 characters");
        error = true;
    }

    // check password length
	if (formObject.password.trim().length < 4) {
		toastr.error("Your password must be at least 4 characters long");
		error = true;
	}

	// check password match
	if (formObject.password != formObject.password_check) {
		toastr.error("Your passwords did not match");
		error = true;
	}

	return !error;
};

var validateEditForm = function (formObject) {
    var error = false;

    // check name to be in interval [1,64]
    if (formObject.name.trim().length < 1) {
        toastr.error("You did not enter a valid name.");
        error = true;
    }
    if (formObject.name.length > 64) {
        toastr.error("Name of the staff should not exceed 64 characters");
        error = true;
    }

    // check username to be in interval [1,64]
    if (formObject.username.trim().length < 1) {
        toastr.error("You did not enter a valid username.");
        error = true;
    }
    if (formObject.username.length > 64) {
        toastr.error("Username should not exceed 64 characters");
        error = true;
    }

    return !error;
};


$(document).ready(function() {
    /* Adds searching and sorting to the table. */
    var table1 = $('#staff-management-table');

    table1.DataTable({
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
            "zeroRecords": "No matching records found",
            "paginate": {
                "previous": "Prev",
                "next": "Next",
                "last": "Last",
                "first": "First"
            }
        },
        "bStateSave": false,

        "columns": [
            {
                "orderable": true
            },
            {
                "orderable": true
            },
            {
                "orderable": true
            },
            {
                "orderable": false
            }
        ],

        "lengthMenu": [
            [20, 50, -1],
            [20, 50, "All"]
        ],
        "pageLength": 20,
        "pagingType": "bootstrap_full_number",

        "columnDefs": [
            {
                'orderable': false,
                'targets': [3]
            },
            {
                "searchable": false,
                "targets": [3]
            }
        ],
        "order": [
            [1, "asc"]
        ]
    });
})