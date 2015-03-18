$(document).ready(function () {

	// link buttons
	$('button.link-button').click(function (e) {
		window.location.href = $(this).attr('data-target');
	});

	// hide expanding menus if not on a sub-page, hide empty sections, then set up clicks
	$('.drop-down-menu').each(function (i) {
		if ($(this).find('li').length == 0) {
			$(this).parent().hide();
		}
		if ($(this).find('li.active').length == 0) {
			subMenuToggle($(this).parent());
		}
	}).parent().find('a:eq(0)').click(function (e) {
		e.preventDefault();
		subMenuToggle($(this).parent());
	});

	// button classes
	$(".edit-button").hover(function () {
		$(this).addClass('btn-warning')
	}, function () {
		$(this).removeClass('btn-warning')
	});

	$(".delete-button").hover(function () {
		$(this).addClass('btn-danger')
	}, function () {
		$(this).removeClass('btn-danger')
	});

	$(".info-button").hover(function () {
		$(this).addClass('btn-info')
	}, function () {
		$(this).removeClass('btn-info')
	});

	$(".confirm-button").hover(function () {
		$(this).addClass('btn-success')
	}, function () {
		$(this).removeClass('btn-success')
	});

	// automatic session timeout
	setAutomaticTimeout();
});

// function to toggle a menu
function subMenuToggle(menuWrapper) {
	if (menuWrapper.attr('data-status') == 'closed') {
		menuWrapper.attr('data-status', 'open');
		menuWrapper.find('a:eq(0)').find('i').removeClass('fa-angle-right').addClass('fa-angle-down');
		menuWrapper.find('ul.drop-down-menu').show();
	} else {
		menuWrapper.attr('data-status', 'closed');
		menuWrapper.find('a:eq(0)').find('i').removeClass('fa-angle-down').addClass('fa-angle-right');
		menuWrapper.find('ul.drop-down-menu').hide();
	}
}

// function to start the automatic timeout
var AUTOMATIC_TIMEOUT_TIMER;
function setAutomaticTimeout() {
	if (AUTOMATIC_TIMEOUT > 0) {
		AUTOMATIC_TIMEOUT_TIMER = setTimeout(function () {
			$('.timeout-modal').removeClass('hide').modal({
				backdrop: 'static',
				keyboard: false
			});
			setTimeout(function () {
				location.href = "/logout";
			}, 30 * 1000);
		}, (AUTOMATIC_TIMEOUT - 30) * 1000);
		$('.timeout-link').click(function (e) {
			e.preventDefault();
			$('.timeout-modal').modal('hide');
			clearTimeout(AUTOMATIC_TIMEOUT_TIMER);
			setAutomaticTimeout();
		});
	}
}

$.fn.filterByData = function (prop, val) {
	return this.filter(
		function () {
			return $(this).data(prop) == val;
		}
	);
};