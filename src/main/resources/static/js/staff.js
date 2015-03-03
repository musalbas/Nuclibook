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
	if (formObject.password.length < 6) {
		toastr.error("Your password must be at least 6 characters long");
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

$(document).ready(function () {
	setUpDataTable('#staff-table', 1, [[1, 1], [1, 1], [1, 1], [0, 0]]);
});