$(document).ready(function () {
	// "show more" links
	$('.more-permissions').click(function (e) {
		e.preventDefault();
		$('#' + $(this).attr('data-target')).slideDown();
		$(this).closest('span').hide();
	})
});

var validateCreateForm = function (formObject) {
	var error = false;

    var roleTitleString = formObject["label"];

    // Check number of title characters to be in interval [1,32]
    if (roleTitleString.trim().length < 1) {
        toastr.error("You did not enter a valid role title.");
        error = true;
    }
    if (roleTitleString.length > 32) {
        toastr.error("Role title should not exceed 32 characters.");
        error = true;
    }
    //TODO Staff_ROLeS when there is no permission selected

	return !error;
};

var validateEditForm = function (formObject) {
    var error = false;

    var roleTitleString = formObject["label"];

    // Check number of title characters to be in interval [1,32]
    if (roleTitleString.trim().length < 1) {
        toastr.error("You did not enter a valid role title.");
        error = true;
    }
    if (roleTitleString.length > 32) {
        toastr.error("Role title should not exceed 32 characters.");
        error = true;
    }

    //TODO Staff_ROLeS when there is no permission selected

    return !error;
};
