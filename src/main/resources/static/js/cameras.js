var validateEditForm = function (formObject) {
    var error = false;

    // check room number
    if (formObject['room-number'].trim().length < 1) {
        toastr.error("You did not enter a room number");
        error = true;
    }
    if (formObject['room-number'].length > 32) {
        toastr.error("Room number should not exceed 32 characters.");
        error = true;
    }

    return !error;
};

var validateCreateForm = function (formObject) {
    var error = false;

	// check room number
    if (formObject['room-number'].trim().length < 1) {
        toastr.error("You did not enter a room number");
        error = true;
    }
    if (formObject['room-number'].length > 32) {
        toastr.error("Room number should not exceed 32 characters.");
        error = true;
    }

    return !error;
};

$(document).ready(function () {
	setUpDataTable('#cameras-table', 0, [[1, 1], [1, 1], [0, 0]]);
});