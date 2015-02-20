var validateCreateForm = function (formObject) {
    var error = false;
    // check name
    if (formObject.name.length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }

    // check if the user inserted a number
    var number = formObject.duration;
    if ($.isNumeric(number) == false) {
        toastr.error("You did not enter a valid number");
    }

    return !error;
};