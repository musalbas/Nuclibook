var assignedStaff = [];

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

	// set up staff adding
	$('.add-staff-member').click(function (e) {
		var selectedOption = $('#staff').find('option:selected');
		var staffId = selectedOption.val();
		var staffName = selectedOption.text();
		addAssignedStaff(staffId, staffName);
	});

	// set up listener for submission
	$('.confirm-booking-button').click(function (e) {
		e.preventDefault();

		confirmBooking();
	});
});

function addAssignedStaff(staffId, staffName) {
	// default?
	if (staffId == 0) return;

	// already in list?
	for (var i in assignedStaff) {
		if (assignedStaff[i] == staffId) {
			toastr.error(staffName + " is already assigned.");
			return;
		}
	}

	// add to output
	var assignedStaffOutput = $('.assigned-staff');
	assignedStaffOutput.html(
		assignedStaffOutput.html()
		+ "<span class=\"assigned-staff-" + staffId + "\">"
		+ staffName
		+ " <a class=\"remove-assigned-staff\" data-id=\"" + staffId + "\" href=\"javascript:;\">[remove]</a>"
		+ "</span>"
	);

	// add to list
	assignedStaff.push(staffId);

	// set up removal button
	$('.remove-assigned-staff').unbind('click').click(function (e) {
		e.preventDefault();
		var sId = $(this).attr('data-id');
		removeAssignedStaff(sId);
	});
}

function removeAssignedStaff(staffId) {
	// remove from HTML
	$('span.assigned-staff-' + staffId).remove();

	// remove from array
	var newAssignedStaff = [];
	for (var i in assignedStaff) {
		if (assignedStaff[i] != staffId) newAssignedStaff.push(assignedStaff[i]);
	}
	assignedStaff = newAssignedStaff;
}

function confirmBooking() {

	// collect data
	var cameraId = $('select[name=camera]').val();
	var tracerId = $('select[name=tracer]').val();
	var tracerDose = $('input[name=tracer-dose]').val();
	var tracerOrderDate = $('input[name=tracer-order-due]').val();
	var tracerOrderNeeded = !$('input[name=no-tracer-order]').is(':checked');
	var staffAssigned = assignedStaff;

	// start validation
	var failedValidation = false;

	// validate camera ID
	if (!$.isNumeric(cameraId)) {
		toastr.error("Please select a camera.");
		failedValidation = true;
	}

	// validate tracer ID
	if (!$.isNumeric(tracerId)) {
		toastr.error("Please select a tracer.");
		failedValidation = true;
	}

	// validate tracer dose
	if (tracerDose.length == 0) {
		toastr.error("Please enter a tracer dose.");
		failedValidation = true;
	}

	// validate tracer order date
	if (tracerOrderNeeded && !tracerOrderDate.match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
		toastr.error("Please select a tracer order date.");
		failedValidation = true;
	}

	// fail?
	if (failedValidation) return;

	// fill in necessary parts of form
	$('input[name=assigned-staff]').val(staffAssigned.join(','));

	// off we go!
	$('.new-booking-form').submit();

}