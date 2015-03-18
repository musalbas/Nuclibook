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
		toastr.error("Hospital number should not exceed 64 characters");
		error = true;
	}

	// check NHS number to be in interval [1,64]
	if (formObject["nhs-number"].trim().length < 1) {
		toastr.error("You did not enter a valid NHS number");
		error = true;
	}
	if (formObject["nhs-number"].length > 64) {
		toastr.error("NHS number should not exceed 64 characters");
		error = true;
	}

	// Check for correct DOB
	if (!formObject["date-of-birth"].match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please enter a valid date of birth.");
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
		toastr.error("Hospital number should not exceed 64 characters");
		error = true;
	}

	// check NHS number to be in interval [1,64]
	if (formObject["nhs-number"].trim().length < 1) {
		toastr.error("You did not enter a valid NHS number");
		error = true;
	}
	if (formObject["nhs-number"].length > 64) {
		toastr.error("NHS number should not exceed 64 characters");
		error = true;
	}

	// Check for correct DOB
	if (!formObject["date-of-birth"].match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please enter a valid date of birth.");
		error = true;
	}
	return !error;
};

var onFormLoadSetup = function () {
	prepareDateSelector();
};

$(document).ready(function () {
	setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);

	$('.import-button').click(function () {
		$('.import-modal').removeClass('hide').modal({
    		backdrop: 'static',
    		keyboard: false
    	});
	});

	$('.import-modal').find('.btn-cancel').unbind('click').click(function (e) {
		$('.import-modal').modal('hide');
	});

	$('.import-modal').find('.btn-save').unbind('click').click(function (e) {
   		// show loading modal
   		enableLoading();

   		// ajax!
   		$.post(
   			'/import',
   			$('.import-form').serialize()
   		).done(function (result) {
   				if (result.substr(0, 5) == 'OKAY:') {
   					// hide modal
   					$('.import-modal').modal('hide');

   					toastr.info(result.substr(5));

   					// hide loading and reload
   					disableLoading(function () {
   						var locationOverride = $('.import-form').find('.location-override');
   						if (locationOverride.length == 1) {
   							location.href = locationOverride.val();
   						} else {
   							location.reload();
   						}
   					});
   				} else if (result == 'failed_validation') {
   					disableLoading(function () {
   						toastr.error('The data you entered was invalid; please check again');
   					});
   				} else if (result == 'no_permission') {
   					disableLoading(function () {
   						toastr.error('You do not have permission to edit or create this item');
   					});
   				} else if (result.substr(0, 7) == 'CUSTOM:') {
   					disableLoading(function () {
   						toastr.error(result.substr(7));
   					});
   				} else {
   					disableLoading(function () {
   						toastr.error('Something went wrong; please try again');
   					});
   				}
   			}
   		).fail(function () {
   				disableLoading(function () {
   					toastr.error('Something went wrong; please try again');
   				});
   			}
   		);
   	});
});