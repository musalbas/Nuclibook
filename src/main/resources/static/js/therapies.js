
//Checking for the Add Form to be ok
var validateCreateForm = function (formObject) {
    var error = false;

    var therapyNameString = formObject["name"];
    var therapyDuration = formObject["default-duration"];
    var therapyMedicineRequired = formObject["medicine-required-name"];
    var therapyMedicineDose = formObject["medicine-dose"];
    var therapyCamera = formObject["camera-type-label"];

    if (therapyNameString.trim().length < 1) {
        toastr.error("You did not enter a valid name for the therapy. Expecting a numer");
        error = true;
    }

    if ($.isNumeric(therapyDuration) == false) {
        toastr.error("You did not enter a valid value for the duration of the therapy")
        error = true;
    }

    //TODO therapyMedicineRequired. Ask the client;

    if ($.isNumeric(therapyMedicineDose) == false) {
        toastr.error("You did not enter a valid value for the medicine dose. Expecting a numer");
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

    if (therapyNameString.trim().length < 1) {
        toastr.error("You did not enter a valid name for the therapy. Expecting a numer");
        error = true;
    }

    if ($.isNumeric(therapyDuration) == false) {
        toastr.error("You did not enter a valid value for the duration of the therapy")
        error = true;
    }

    //TODO therapyMedicineRequired. Ask the client;

    if ($.isNumeric(therapyMedicineDose) == false) {
        toastr.error("You did not enter a valid value for the medicine dose. Expecting a numer");
        error = true;
    }

    //TODO therapyCamera. Ask the client.


    return !error;
};