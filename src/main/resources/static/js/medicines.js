

var validateCreateForm = function (formObject) {
    var error = false;
    // check name
    if (formObject.name.trim().length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }

    // check if the user inserted a number
    var number = formObject['order-time'];
    if ($.isNumeric(number) == false) {
        toastr.error("You did not enter a valid order time. Expecting a number.");
		error = true;
    }

    return !error;
};

var validateEditForm = function (formObject) {
	var error = false;
	// check name
	if (formObject.name.trim().length < 1) {
		toastr.error("You did not enter a valid name");
		error = true;
	}

	// check if the user inserted a number
	var number = formObject['order-time'];
	if ($.isNumeric(number) == false) {
		toastr.error("You did not enter a valid order time. Expecting a number");
		error = true;
	}

	return !error;
};