var validateCreateForm = function (formObject) {
	var error = false;

	var format = new RegExp('\\d{4}\\-\\d{2}\\-\\d{2}\\ \\d{2}:\\d{2}');
	if (!format.test(formObject['from'])) {
		toastr.error("You did not enter the 'from' date in YYYY-MM-DD HH:MM format.");
		error = true;
	}
	if (!format.test(formObject['to'])) {
		toastr.error("You did not enter the 'to' date in YYYY-MM-DD HH:MM format.");
		error = true;
	}

	return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

	var format = new RegExp('\\d{4}\\-\\d{2}\\-\\d{2}\\ \\d{2}:\\d{2}');
	if (!format.test(formObject['from'])) {
		toastr.error("You did not enter the 'from' date in YYYY-MM-DD HH:MM format.");
		error = true;
	}
	if (!format.test(formObject['to'])) {
		toastr.error("You did not enter the 'to' date in YYYY-MM-DD HH:MM format.");
		error = true;
	}

	return !error;
};