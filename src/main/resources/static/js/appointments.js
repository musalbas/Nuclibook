var patientId = 0;
var therapyId = 0;

$(document).ready(function () {

	$('.select-patient').click(function () {
		// get id and name
		patientId = $(this).attr('data-id');
		$('.patient-selected').html($(this).attr('data-name'));

		// open next page
		$('#page-one').slideUp(500);
		$('#page-two').slideDown(500);
	});

	$('#go-back-to-select-patient').click(function () {
		// open prev page
		$('#page-one').slideDown(500);
		$('#page-two').slideUp(500);
	});

	$('.select-therapy').click(function () {
		// get id and name
		therapyId = $(this).attr('data-id');
		$('.therapy-selected').html($(this).attr('data-name'));

		// open next page
		$('#page-two').slideUp(500);
		$('#page-three').slideDown(500);
	});

	$('#go-back-to-select-therapy').click(function () {
		// open prev page
		$('#page-two').slideDown(500);
		$('#page-three').slideUp(500);
	});

	$('#view-available-appointments').click(function () {
		// hide buttons
		$(this).slideUp(300);
		$('#go-back-to-select-therapy').hide();

		// show calendar
		$('.calendar').show().fullCalendar({
			header: {

				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},

            defaultView: 'agendaWeek',
			editable: true
		});
	});

	// datatables
	setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
	setUpDataTable('#therapies-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);
});