var validateCreateForm = function (formObject) {
    var error = false;

    var format = new RegExp('\\d{4}\\-\\d{2}\\-\\d{2}');
    if (!format.test(formObject['from-date'])) {
        toastr.error("You did not enter the 'from' date in YYYY-MM-DD format.");
        error = true;
    }
    if (!format.test(formObject['to-date'])) {
        toastr.error("You did not enter the 'to' date in YYYY-MM-DD format.");
        error = true;
    }

    if (new Date(formObject['from-date'] + " " + formObject['from-time']) >= new Date(formObject['to-date'] + " " + formObject['to-time'])) {
        toastr.error("The 'from' date cannot be after the 'to' date.");
        error = true;
    }

    return !error;
};

var validateEditForm = function (formObject) {
    var error = false;

    var format = new RegExp('\\d{4}\\-\\d{2}\\-\\d{2}');
    if (!format.test(formObject['from-date'])) {
        toastr.error("You did not enter the 'from' date in YYYY-MM-DD format.");
        error = true;
    }
    if (!format.test(formObject['to-date'])) {
        toastr.error("You did not enter the 'to' date in YYYY-MM-DD format.");
        error = true;
    }

    if (new Date(formObject['from-date'] + " " + formObject['from-time']) >= new Date(formObject['to-date'] + " " + formObject['to-time'])) {
        toastr.error("The 'from' date cannot be after the 'to' date.");
        error = true;
    }

    return !error;
};

var onFormLoadSetup = function () {
    prepareDateSelector();
    prepareTimeSelector();
};

$(document).ready(function () {
    setUpDataTable('#generic-events', [[1, 1], [1, 1], [0, 0]], {
        order: [0, 'asc']
    });
});