$(document).ready(function() {

	// link buttons
	$('button.link-button').click(function(e) {
		window.location.href = $(this).attr('data-target');
	});

});