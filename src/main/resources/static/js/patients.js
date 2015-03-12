var validateEditForm = function (formObject) {
    var error = false;

    // check name to be in interval [1,64]
    if (formObject.name.trim().length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }
    if (formObject.name.length > 64) {
        toastr.error("Name of the patient should not exceed 64 characters");
        error = true;
    }

    // check hospital number to be in interval [1,64]
    if (formObject["hospital-number"].trim().length < 1) {
        toastr.error("You did not enter a valid hospital number");
        error = true;
    }
    if (formObject["hospital-number"].length > 64) {
        toastr.error("Hospital Number should not exceed 64 characters");
        error = true;
    }
    //Check for correct DOB
    console.log(formObject["year-of-birth"]);
    if (formObject["year-of-birth"] == "Year" || formObject["year-of-birth"] == undefined) {
        toastr.error("Please select an year for the patient birth.");
        error = true;
    }

    if (formObject["month-of-birth"] == "Month" || formObject["month-of-birth"] == undefined) {
        toastr.error("Please select a month for the patient birth.");
        error = true;
    }

    if (formObject["day-of-birth"] == "Day" || formObject["day-of-birth"] == undefined) {
        toastr.error("Please select a day for the patient birth.");
        error = true;
    }
    return !error;
};

var validateCreateForm = function (formObject) {
    var error = false;

    // check name to be in interval [1,64]
    if (formObject.name.trim().length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }
    if (formObject.name.length > 64) {
        toastr.error("Name of the patient should not exceed 64 characters");
        error = true;
    }

    // check hospital number to be in interval [1,64]
    if (formObject["hospital-number"].trim().length < 1) {
        toastr.error("You did not enter a valid hospital number");
        error = true;
    }
    if (formObject["hospital-number"].length > 64) {
        toastr.error("Hospital Number should not exceed 64 characters");
        error = true;
    }
    //Check for correct DOB
    console.log(formObject["year-of-birth"]);
    if (formObject["year-of-birth"] == "Year" || formObject["year-of-birth"] == undefined) {
        toastr.error("Please select an year for the patient birth.");
        error = true;
    }

    if (formObject["month-of-birth"] == "Month" || formObject["month-of-birth"] == undefined) {
        toastr.error("Please select a month for the patient birth.");
        error = true;
    }

    if (formObject["day-of-birth"] == "Day" || formObject["day-of-birth"] == undefined) {
        toastr.error("Please select a day for the patient birth.");
        error = true;
    }
    return !error;
};

var onFormLoadSetup = function () {
	prepareDateSelector();
};

$(document).ready(function () {
    setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
});