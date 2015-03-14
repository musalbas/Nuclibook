$(document).ready(function () {

	// set the default tracer
	var defaultTracer = $('input[name=default-tracer]').val();
	$('#tracer').find('option[value=' + defaultTracer + ']').attr('selected', 'selected');

	// set up the tracer order date picker
	prepareDateSelector();

	// set listener for "no tracer order"
	$('#no-tracer-order').change(function (e) {
		if ($(this).is(':checked')) {
			$('.tracer-order-wrapper').slideUp(200);
		} else {
			$('.tracer-order-wrapper').slideDown(200);
		}
	});

});