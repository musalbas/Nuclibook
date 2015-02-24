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

    if (roleTitleString.trim().length < 1) {
        toastr.error("You did not enter a valid role title");
        error = true;
    }
    //TODO Staff_ROLeS when there is no permission selected

	return !error;
};