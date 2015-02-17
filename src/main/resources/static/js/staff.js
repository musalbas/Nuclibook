// TODO: use the toastr library for errors, instead of alerts

var validateEditForm = function (formObject) {
	var error = false;

	// check name
	if (formObject.name.length < 1) {
		alert("You did not enter a valid name");
		error = true;
	}

	// check username
	if (formObject.username.length < 1) {
		alert("You did not enter a valid username");
		error = true;
	}

	return !error;
};

var validateCreateForm = function (formObject) {
	var error = false;

	// check name
	if (formObject.name.length < 1) {
		alert("You did not enter a valid name");
		error = true;
	}

	// check username
	if (formObject.username.length < 1) {
		alert("You did not enter a valid username");
		error = true;
	}

	// check password length
	if (formObject.password.length < 4) {
		alert("Your password must be at least 4 characters long");
		error = true;
	}

	// check password match
	if (formObject.password != formObject.password_check) {
		alert("Your passwords did not match");
		error = true;
	}

	return !error;
};