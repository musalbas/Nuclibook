var validateEditForm = function (formObject) {
    var error = false;

    // check room number
    if (formObject['room-number'].length < 1) {
        toastr.error("You did not enter a room number");
        error = true;
    }

    return !error;
};

var validateCreateForm = function (formObject) {
    var error = false;

	// check room number
    if (formObject['room-number'].length < 1) {
        toastr.error("You did not enter a room number");
        error = true;
    }

    return !error;
};