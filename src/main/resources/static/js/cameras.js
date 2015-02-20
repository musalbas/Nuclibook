/**
 * Created by GEORGE RADUTA on 11.02.2015.
 */

var validateEditForm = function (formObject) {
    var error = false;

    // check name TODO
    if (formObject.name.length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }

    return !error;
};

var validateCreateForm = function (formObject) {
    var error = false;

    // check name TODO
    if (formObject.name.length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }

    return !error;
};