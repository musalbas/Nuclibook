

var validateCreateForm = function (formObject) {
    var error = false;
    // check name to be in interval [1,64]
    if (formObject.name.trim().length < 1) {
        toastr.error("You did not enter a valid name.");
        error = true;
    }
    if (formObject.name.length > 64) {
        toastr.error("Name of the medicine should not exceed 64 characters.");
        error = true;
    }

    // check if the user inserted a number
    var number = formObject['order-time'];
    if ($.isNumeric(number) == false) {
        toastr.error("You did not enter a valid order time. Expecting a number.");
		error = true;
    } else {
        if (number < '0' || number > '2147483647') {
            toastr.error("Order time should be a positive number smaller than 2,147,483,647");
            error = true;
        }
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
        toastr.error("Name of the medicine should not exceed 64 characters.");
        error = true;
    }

    // check if the user inserted an integer
    var number = formObject['order-time'];
    if ($.isNumeric(number) == false) {
        toastr.error("You did not enter a valid order time. Expecting a number.");
        error = true;
    } else {
        if (number < '0' || number > '2147483647') {
            toastr.error("Order time should be a positive number smaller than 2,147,483,647");
            error = true;
        }
    }

    return !error;
};