var validateCreateForm = function (formObject) {
	var error = false;
	var therapyNameString = formObject["name"];
	var therapyDuration = formObject["default-duration"];
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
	if ($.isNumeric(therapyDuration) == false) {
		toastr.error("Please enter a valid value for the duration of the therapy. Expecting a number.");
		error = true;
	} else {
		if (therapyDuration > 2147483647 || therapyDuration < 0) {
			toastr.error("Therapy duration should be a positive number smaller than 2,147,483,647");
			error = true;
		}
	}

	// dose
	if (therapyTracerDose.trim().length < 1) {
		toastr.error("Please enter a valid value for the tracer dose.");
		error = true;
	}
	if (therapyTracerDose.length > 32) {
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
	setUpDataTable('#therapies-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);

	$('.add-question').click(function () {
		var targetDiv = $('.patient-questions');
		var children = targetDiv.children('input').length;
		targetDiv.append('<br /><input class="form-control" type="text" name="patient-question-' + children + '" placeholder="Leave blank if not required."/>');
	});
});

customFieldPrefill = function (key, data) {
	if (data == null || data.length == 0) {
		return '<input class="form-control patient-question-input" type="text" name="patient-question-0" placeholder="Leave blank if not required."/>';
	}

	var output = '';
	if (key.substr(7) == 'patient-questions') {
		for (var i in data) {
			output += (output == '' ? '' : '<br />') + '<input class="form-control patient-question-input" type="text" name="patient-question-' + i + '" placeholder="Leave blank if not required." value="' + data[i] + '"/>'
		}
	}
	return output;
};