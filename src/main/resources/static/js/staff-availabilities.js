var validateCreateForm = function (formObject) {
	var error = false;

	var format = new RegExp('\\d{2}:\\d{2}');
	if (!format.test(formObject['start-time'])) {
		toastr.error("You did not enter the start time in HH:MM format.");
		error = true;
	}
	if (!format.test(formObject['end-time'])) {
		toastr.error("You did not enter the end time in HH:MM format.");
		error = true;
	}

	if (new Date('2000-01-01 ' + formObject['start-time']) >= new Date('2000-01-01 ' + formObject['end-time'])) {
		toastr.error("The start time cannot be after the end time.");
		error = true;
	}

	return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

	var format = new RegExp('\\d{2}:\\d{2}');
	if (!format.test(formObject['start-time'])) {
		toastr.error("You did not enter the start time in HH:MM format.");
		error = true;
	}
	if (!format.test(formObject['end-time'])) {
		toastr.error("You did not enter the end time in HH:MM format.");
		error = true;
	}

	if (new Date('2000-01-01 ' + formObject['start-time']) >= new Date('2000-01-01 ' + formObject['end-time'])) {
		toastr.error("The start time cannot be after the end time.");
		error = true;
	}

	return !error;
};

$(document).ready(function () {
	setUpDataTable('#staff-availabilities', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
});