var validateCreateForm = function (formObject) {
	var error = false;

	if (!formObject['from-date'].match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please enter a valid 'from' date.");
		error = true;
	}
	if (!formObject['from-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid 'from' time.");
		error = true;
	}
	if (!formObject['to-date'].match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please enter a valid 'to' date.");
		error = true;
	}
	if (!formObject['to-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid 'to' time.");
		error = true;
	}

	if (new Date(formObject['from-date'] + ' ' + formObject['from-time']) >= new Date(formObject['to'] + ' ' + formObject['to-time'])) {
		toastr.error("The 'from' date must be before the 'to' date.");
		error = true;
	}

	return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

	if (!formObject['from-date'].match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please enter a valid 'from' date.");
		alert(formObject['from-date']);
		error = true;
	}
	if (!formObject['from-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid 'from' time.");
		alert(formObject['from-time']);
		error = true;
	}
	if (!formObject['to-date'].match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please enter a valid 'to' date.");
		alert(formObject['to-date']);
		error = true;
	}
	if (!formObject['to-time'].match(/[0-9]{2}:[0-9]{2}/)) {
		toastr.error("Please enter a valid 'to' time.");
		alert(formObject['to-time']);
		error = true;
	}

	if (new Date(formObject['from-date'] + ' ' + formObject['from-time']) >= new Date(formObject['to'] + ' ' + formObject['to-time'])) {
		toastr.error("The 'from' date must be before the 'to' date.");
		error = true;
	}

	return !error;
};

var onFormLoadSetup = function () {
	prepareDateSelector();
	prepareTimeSelector();
};

$(document).ready(function () {
	setUpDataTable('#staff-absences', 0, [[1, 1], [1, 1], [0, 0]]);
});