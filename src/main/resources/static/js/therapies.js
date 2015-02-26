
//Checking for the Add Form to be ok
var validateCreateForm = function (formObject) {
    var error = false;

    var therapyNameString = formObject["name"];
    var therapyDuration = formObject["default-duration"];
    var therapyMedicineRequired = formObject["medicine-required-name"];
    var therapyMedicineDose = formObject["medicine-dose"];
    var therapyCamera = formObject["camera-type-label"];

    // Check range to be [1,64]
    if (therapyNameString.trim().length < 1) {
        toastr.error("You did not enter a valid name for the therapy.");
        error = true;
    }
    if (therapyNameString.trim().length > 64) {
        toastr.error("Therapy name should not exceed 64 characters.");
        error = true;
    }

    // Check if the duration represents a number and is in integer range
    if ($.isNumeric(therapyDuration) == false) {
        toastr.error("You did not enter a valid value for the duration of the therapy. Excepting a number.")
        error = true;
    } else {
        if (therapyDuration > '2147483647' || therapyDuration < 0) {
            toastr.error("Therapy duration should be a positive number smaller then 2,147,483,647")
            error = true;
        }
    }

    //TODO therapyMedicineRequired. Ask the client;


    if (therapyMedicineDose.trim().length < 1) {
        toastr.error("You did not enter a valid value for the medicine dose.");
        error = true;
    }
    if (therapyMedicineDose.trim().length > 32) {
        toastr.error("Therapy duration should not exceed 32 characters.");
        error = true;
    }

    //TODO therapyCamera. Ask the client.

    return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

    var therapyNameString = formObject["name"];
    var therapyDuration = formObject["default-duration"];
    var therapyMedicineRequired = formObject["medicine-required-name"];
    var therapyMedicineDose = formObject["medicine-dose"];
    var therapyCamera = formObject["camera-type-label"];

    // Check range to be [1,64]
    if (therapyNameString.trim().length < 1) {
        toastr.error("You did not enter a valid name for the therapy.");
        error = true;
    }
    if (therapyNameString.trim().length > 64) {
        toastr.error("Therapy name should not exceed 64 characters.");
        error = true;
    }

    // Check if the duration represents a number and is in integer range
    if ($.isNumeric(therapyDuration) == false) {
        toastr.error("You did not enter a valid value for the duration of the therapy. Excepting a number.")
        error = true;
    } else {
        if (therapyDuration > '2147483647' || therapyDuration < 0) {
            toastr.error("Therapy duration should be a positive number smaller then 2,147,483,647")
            error = true;
        }
    }

    //TODO therapyMedicineRequired. Ask the client;


    if (therapyMedicineDose.trim().length < 1) {
        toastr.error("You did not enter a valid value for the medicine dose.");
        error = true;
    }
    if (therapyMedicineDose.trim().length > 32) {
        toastr.error("Therapy duration should not exceed 32 characters.");
        error = true;
    }

    //TODO therapyCamera. Ask the client.
    return !error;
};