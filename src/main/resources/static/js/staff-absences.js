var validateCreateForm = function (formObject) {
	var error = false;

	var format = new RegExp('\\d{4}\\-\\d{2}\\-\\d{2}\\');
	if (!format.test(formObject['from'])) {
		toastr.error("You did not enter the 'from' date in YYYY-MM-DD format.");
		error = true;
	}
	if (!format.test(formObject['to'])) {
		toastr.error("You did not enter the 'to' date in YYYY-MM-DD format.");
		error = true;
	}

	if (new Date(formObject['from']) >= new Date(formObject['to'])) {
		toastr.error("The 'from' date cannot be after the 'to' date.");
		error = true;
	}

	return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

	var format = new RegExp('\\d{4}\\-\\d{2}\\-\\d{2}\\');
	if (!format.test(formObject['from'])) {
		toastr.error("You did not enter the 'from' date in YYYY-MM-DD format.");
		error = true;
	}
	if (!format.test(formObject['to'])) {
		toastr.error("You did not enter the 'to' date in YYYY-MM-DD format.");
		error = true;
	}

	if (new Date(formObject['from']) >= new Date(formObject['to'])) {
		toastr.error("The 'from' date cannot be after the 'to' date.");
		error = true;
	}

	return !error;
};

$(document).ready(function () {
	setUpDataTable('#staff-absences', 0, [[1, 1], [1, 1], [0, 0]]);
});