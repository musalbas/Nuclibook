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