$(document).ready(function () {
	// "show more" links
	$('.more-permissions').click(function (e) {
		e.preventDefault();
		$('#' + $(this).attr('data-target')).show();
		$(this).closest('span').hide();
	})
});

var validateCreateForm = function (formObject) {
	var error = false;

	// TODO

	return !error;
};