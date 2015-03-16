var validateCreateForm = function (formObject) {
	var error = false;

	if (!formObject['start-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid start time.");
		alert(formObject['to-time']);
		error = true;
	}

	if (!formObject['end-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid end time.");
		alert(formObject['to-time']);
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

	if (!formObject['start-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid start time.");
		alert(formObject['to-time']);
		error = true;
	}

	if (!formObject['end-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid end time.");
		alert(formObject['to-time']);
		error = true;
	}

	if (new Date('2000-01-01 ' + formObject['start-time']) >= new Date('2000-01-01 ' + formObject['end-time'])) {
		toastr.error("The start time cannot be after the end time.");
		error = true;
	}

	return !error;
};

var onFormLoadSetup = function () {
	prepareTimeSelector();
};

$(document).ready(function () {
	setUpDataTable('#staff-availabilities', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
});