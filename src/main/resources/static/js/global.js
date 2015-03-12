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

    $(".edit-button").hover(function(){
        $(this).addClass('btn-warning')
    }, function(){
        $(this).removeClass('btn-warning')
    });

    $(".delete-button").hover(function(){
        $(this).addClass('btn-danger')
    }, function(){
        $(this).removeClass('btn-danger')
    });

    setTimeout(function () {
        forcePasswordChange();
    }, 100);
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

var forcePasswordChange = function () {
    window.location.replace("/profile?changepw=1")
}