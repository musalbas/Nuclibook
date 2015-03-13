$(document).ready(function () {

	// expanding panels
	$('.panel .panel-heading').click(function (e) {
		e.preventDefault();

		var icon = $(this).find('h3 i');
		if (icon.hasClass('fa-angle-up')) {
			icon.removeClass('fa-angle-up').addClass('fa-angle-down');
		} else {
			icon.removeClass('fa-angle-down').addClass('fa-angle-up');
		}

		$(this)
			.css('cursor', 'pointer')
			.parent().find('.panel-body').slideToggle(300);
	});

});