var validateCreateForm = function (formObject) {
	var error = false;
	var therapyNameString = formObject["name"];
	var therapyTracerDose = formObject["tracer-dose"];

	// Check range to be [1,64]
	if (therapyNameString.trim().length < 1) {
		toastr.error("Please enter a valid name for the therapy.");
		error = true;
	}
	if (therapyNameString.length > 64) {
		toastr.error("Therapy name should not exceed 64 characters.");
		error = true;
	}

	// Check if the duration represents a number and is in integer range
	/*if ($.isNumeric(therapyDuration) == false) {
		toastr.error("Please enter a valid value for the duration of the therapy. Expecting a number.");
		error = true;
	} else {
		if (therapyDuration > 2147483647 || therapyDuration < 0) {
			toastr.error("Therapy duration should be a positive number smaller than 2,147,483,647");
			error = true;
		}
	}*/

	// dose
	if (therapyTracerDose.trim().length < 1) {
		toastr.error("Please enter a valid value for the tracer dose.");
		error = true;
	}
	if (therapyTracerDose.length > 32) {
		toastr.error("Tracer dose should not exceed 32 characters.");
		error = true;
	}

	// loop through patient questions
	for (var fieldName in formObject) {
		if (fieldName.indexOf('patient-question') > -1) {
			var patientQuestion = formObject[fieldName];

			//check range [<256]
			if (patientQuestion.length > 256) {
				toastr.error("Patient questions should not exceed 256 characters.");
				error = true;
			}

		}
	}

	return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

	var therapyNameString = formObject["name"];
	var therapyDuration = formObject["default-duration"];
	var therapyTracerDose = formObject["tracer-dose"];

	// Check range to be [1,64]
	if (therapyNameString.trim().length < 1) {
		toastr.error("Please enter a valid name for the therapy.");
		error = true;
	}
	if (therapyNameString.trim().length > 64) {
		toastr.error("Therapy name should not exceed 64 characters.");
		error = true;
	}

	// Check if the duration represents a number and is in integer range
	if ($.isNumeric(therapyDuration) == false) {
		toastr.error("Please enter a valid value for the duration of the therapy. Expecting a number.");
		error = true;
	} else {
		if (therapyDuration > 2147483647 || therapyDuration < 0) {
			toastr.error("Therapy duration should be a positive number smaller than 2,147,483,647");
			error = true;
		}
	}

	//TODO therapyTracerRequired. Ask the client;

	if (therapyTracerDose.trim().length < 1) {
		toastr.error("Please enter a valid value for the tracer dose.");
		error = true;
	}
	if (therapyTracerDose.trim().length > 32) {
		toastr.error("Tracer dose should not exceed 32 characters.");
		error = true;
	}

	//loop through patient questions
	for (var fieldName in formObject) {
		if (fieldName.indexOf('patient-question') > -1) {
			var patientQuestion = formObject[fieldName];

			//check range [<256]
			if (patientQuestion.length > 256) {
				toastr.error("Patient questions should not exceed 256 characters.");
				error = true;
			}
		}
	}

	return !error;
};

$(document).ready(function () {
	setUpDataTable('#therapies-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);

	// link for adding booking sections
	$('.add-booking-section').click(function () {
		var targetDiv = $('.booking-sections');
		var childCount = targetDiv.children('div.row').length;
		targetDiv.append('<div class="row"><div class="col-sm-3"><select class="form-control stacked-form-control" name="booking-section-' + childCount + 'a"><option value="busy">Busy</option><option value="wait">Wait</option></select></div><div class="col-sm-9"><input class="form-control stacked-form-control" type="text" name="booking-section-' + childCount + 'b" placeholder="Leave blank if not required."/></div></div>');
	});

	// link for adding patient questions
	$('.add-patient-question').click(function () {
		var targetDiv = $('.patient-questions');
		var childCount = targetDiv.children('input').length;
		targetDiv.append('<input class="form-control stacked-form-control" type="text" name="patient-question-' + childCount + '" placeholder="Leave blank if not required."/>');
	});
});

customFieldPrefill = function (key, data) {
	var output, i;

	if (key.substr(7) == 'patient-questions') {
		if (data == null || data.length == 0) {
			return '<input class="form-control" type="text" name="patient-question-0" placeholder="Leave blank if not required."/>';
		}

		output = '';
		for (i in data) {
			output += '<input class="form-control stacked-form-control" type="text" name="patient-question-' + i + '" placeholder="Leave blank if not required." value="' + data[i] + '"/>'
		}
		return output;
	}

	if (key.substr(7) == 'booking-pattern-sections') {
		if (data == null || data.length == 0) {
			return '<div class="row"><div class="col-sm-3"><select class="form-control stacked-form-control" name="booking-section-0a"><option value="busy">Busy</option><option value="wait">Wait</option></select></div><div class="col-sm-9"><input class="form-control stacked-form-control" type="text" name="booking-section-0b" placeholder="Leave blank if not required."/></div></div>';
		}

		output = '';
		for (i in data) {
			output += '<input class="form-control stacked-form-control" type="text" name="patient-question-' + i + '" placeholder="Leave blank if not required." value="' + data[i] + '"/>'
		}
		return output;
	}

	return "";
};