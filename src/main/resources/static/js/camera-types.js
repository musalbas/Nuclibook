var validateEditForm = function (formObject) {
    var error = false;

    // check room number
    if (formObject['label'].trim().length < 1) {
        toastr.error("You did not enter a label for the camera type");
        error = true;
    }
    if (formObject['label'].length > 32 ) {
        toastr.error("Camera Type Label should not exceed 32 characters.");
        error = true;
    }

    return !error;
};

var validateCreateForm = function (formObject) {
    var error = false;
    if (formObject['label'].trim().length < 1) {
        toastr.error("You did not enter a label for the camera type");
        error = true;
    }
    if (formObject['label'].length > 32) {
        toastr.error("Camera Type Label should not exceed 32 characters.");
        error = true;
    }

    return !error;
};