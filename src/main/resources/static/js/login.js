function keepFocus() {
	var focusedElement = $(stage == 2 ? '#password' : '#username');

	focusedElement.focus();

	focusedElement.unbind("blur").blur(function () {
		setTimeout(function () {
			focusedElement.focus();
		}, 0);
	});
}

// Check if capslock is on, show tooltip if it is
function checkCapsLockStatus() {
	$('[type=password]').keypress(function (e) {
		var $password = $(this),
			tooltipVisible = $('.tooltip').is(':visible'),
			s = String.fromCharCode(e.which);

		//Tests if letter is upper case and the shift key is NOT pressed.
		if (s.toUpperCase() === s && s.toLowerCase() !== s && !e.shiftKey) {
			if (!tooltipVisible)
				$password.tooltip('show');
		} else {
			if (tooltipVisible)
				$password.tooltip('hide');
		}

		//Hide the tooltip when moving away from the password field
		$password.blur(function (e) {
			$password.tooltip('hide');
		});
	});
}

$(document).ready(function () {
	keepFocus();

	// button click listeners
	$('button').click(function (e) {
		e.preventDefault();
		$('#login-form').submit();
		$('button').attr('disabled', 'disabled');
		$('input').attr('readonly', 'readonly');
	});

	// submit listener
	$('#login-form').submit(function (e) {
		$('button').attr('disabled', 'disabled');
		$('input').attr('readonly', 'readonly');
	});
});

$(window).focus(function () {
	keepFocus();
});