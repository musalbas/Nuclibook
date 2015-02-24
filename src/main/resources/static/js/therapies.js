var validateCreateForm = function (formObject) {
    var error = false;

    var therapyNameString = formObject["name"];
    var therapyDuration = formObject["default-duration-mins"];
    var therapyMedicineRequired = formObject["medicine-required-name"];
    var therapyMedicineDose = formObject["medicine-dose"];
    var therapyCamera = formObject["camera-type-label"];

    if (therapyNameString.trim().length < 1) {
        toastr.error("You did not enter a valid name for the therapy");
        error = true;
    }

    if ($.isNumeric(therapyDuration) == false) {
        toastr.error("You did not enter a valid value for the duration of the therapy")
        error = true;
    }

    //TODO therapyMedicineRequired. Ask the client;

    if (therapyMedicineDose.trim().length < 1) {
        toastr.error("You did not enter a valid value for the medicine dose");
        error = true;
    }

    //TODO therapyCamera. Ask the client.

    return !error;
};

var validateEditForm = function (formObject) {
	var error = false;

	// TODO

	return !error;
};