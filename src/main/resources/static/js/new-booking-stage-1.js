var patientId = 0;
var therapyId = 0;

$(document).ready(function () {

	/*********************
	 FIRST STAGE NAVIGATION
	 **********************/

	// select patient handled in onTableReloadFinished()

	$('#go-back-to-select-patient').click(function () {
		// open prev page
		$('#page-one').slideDown(500);
		$('#page-two').slideUp(500);
	});

	$('.select-therapy').click(function () {
		// get id and name
		therapyId = $(this).attr('data-id');
		$('.therapy-selected').html($(this).attr('data-name') + '&nbsp;<i class="fa fa-fw fa-info-circle" data-toggle="tooltip" data-placement="top" title="' + $(this).attr('data-advice').replace("//", "\n\n") + '"></i>');

		// reload tooltips
		$("body").tooltip({selector: '[data-toggle=tooltip]'});

		// set specified cameras
		var rawCameras = ($(this).attr('data-cameras').split(":"))[1].split(",");
		var cameras = [];
		for (var c in rawCameras) {
			if (rawCameras[c] != "") cameras.push(rawCameras[c]);
		}
		if (cameras.length > 0) {
			selectedCameras = cameras;
		}

		// open next page
		$('#page-two').slideUp(500);
		$('#page-three').slideDown(500);
	});

	$('#go-back-to-select-therapy').click(function () {
		// open prev page
		$('#page-two').slideDown(500);
		$('#page-three').slideUp(500);
	});

	/***********************
	 FIRST STAGE DATA TABLES
	 ***********************/

	setUpDataTable('#patients-table', [[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [0, 0]], {
		order: [0, 'asc'],
		processing: true,
		serverSide: true,
		ajax: '/ajax/patient-data/1'
	});
	setUpDataTable('#therapies-table', [[1, 1], [1, 1], [1, 1], [1, 1], [0, 0]], {
		order: [0, 'asc']
	});

	// "show more" links
	$('.more-camera-types').click(function (e) {
		e.preventDefault();
		$('#' + $(this).attr('data-target')).slideDown();
		$(this).closest('span').hide();
	});

	/********************
	 SECOND STAGE CALENDAR
	 *********************/

	// initial set up
	var calendar = $('.calendar');

	// when the "show bookings" button is clicked...
	$('#view-available-bookings').click(function () {
		// hide buttons
		$(this).slideUp(300);
		$('#go-back-to-select-therapy').hide();

		// add button for saving the bookings
		$('#page-three-sub-div').find('div.col-sm-4').eq(2).html('<button class="btn btn-primary" id="saveAppointments" disabled>Select Time Slot(s) Below...</button>');

		// variables for modal
		var startTimeObject;
		var endTimeObject;

		// modal inputs
		var startTimeInput = $('input[name=start-time]');
		var endTimeInput = $('input[name=end-time]');

		// function to be ran when a slot is selected
		var onSelectFunction = function (start, end, allday) {
			// assign outer items
			startTimeObject = start;
			endTimeObject = end;

			// get options to pre-fill modal
			var startHours = (start.getHours() < 10 ? '0' : '') + start.getHours();
			var startMins = (start.getMinutes() < 10 ? '0' : '') + start.getMinutes();
			var endHours = (end.getHours() < 10 ? '0' : '') + end.getHours();
			var endMins = (end.getMinutes() < 10 ? '0' : '') + end.getMinutes();

			if (start.getTime() < (new Date()).getTime()) {
				toastr.error("You cannot make a booking in the past");
				calendar.fullCalendar('unselect');
				return;
			}
            if (start.getTime() >= end.getTime()) {
                toastr.error("Hours are not properly selected");
                return;
            }
			// pre-fill modal
			startTimeInput.val(startHours + ':' + startMins);
			endTimeInput.val(endHours + ':' + endMins);
			prepareTimeSelector();

			// open Modal
			var timeModal = $('.time-modal');
			timeModal.removeClass('hide').modal('show');
			setTimeout(function () {
				timeModal.find('select').eq(0).focus().select();
			}, 200);
		};

		// show calendar
		calendar = setupCalendar($('.calendar'), onSelectFunction, {
			linkEvents: false,
			selectable: true
		});

		// start building JSON to send
		var datesSelectedJSON;
		datesSelectedJSON = "{\"patientId\":" + patientId + "," + "\"therapyId\":" + therapyId + "," + "\"bookingSections\":[";

		// click ok to keep appointment;
		$('.btn-save').click(function () {
            console.log()
			// get start/end time
			var startTime = startTimeInput.val();

			var endTime = endTimeInput.val();

			// check valid times were entered
			if (!startTime.match(/[0-9]{2}:[0-9]{2}/) || !endTime.match(/[0-9]{2}:[0-9]{2}/)) {
				toastr.error("Please select valid start and end times.");
				return;
			}

			// check start < end
            var date1 = "2000-01-01 " + startTime;
            var date2 = "2000-01-01 " + endTime;
            var indexOfComparingDates = date1.localeCompare(date2);
 			if (indexOfComparingDates != -1) {
				toastr.error("Hours are not properly selected.");
				return;
			}

			// enable button
			$('#saveAppointments').removeAttr('disabled').html("Save Booking");

			// add to json
			var fullStartTime = startTimeObject.getFullYear() + '-'
				+ (startTimeObject.getMonth() < 10 ? '0' : '') + (startTimeObject.getMonth() + 1) + '-'
				+ (startTimeObject.getDate() < 10 ? '0' : '') + startTimeObject.getDate() + 'T'
				+ startTime + ":00";
			var fullEndTime = endTimeObject.getFullYear() + '-'
				+ (endTimeObject.getMonth() < 10 ? '0' : '') + (endTimeObject.getMonth() + 1) + '-'
				+ (endTimeObject.getDate() < 10 ? '0' : '') + endTimeObject.getDate() + 'T'
				+ endTime + ":00";
			datesSelectedJSON += "{\"startTime\":\"" + fullStartTime + "\",";
			datesSelectedJSON += "\"endTime\":\"" + fullEndTime + "\"},";

			// add to calendar
			calendar.fullCalendar('renderEvent',
				{
					title: $('.therapy-selected').text() + ":\n" + $('.patient-selected').eq(0).text(),
					start: fullStartTime,
					end: fullEndTime,
					msg: "Start time: <strong>" + startTime + "</strong>" +
					"<br/>End time: <strong>" + endTime + "</strong>",
					allDay: false
				},
				true
			);
			calendar.fullCalendar('unselect');

			// close modal
			$('.time-modal').modal('hide');
		});

		// Function to send the user to the next page.
		$('#saveAppointments').click(function () {
			// finish the JSON
			datesSelectedJSON = datesSelectedJSON.substring(0, datesSelectedJSON.length - 1);
			datesSelectedJSON += "]}";

			// send!
			$('#jsonToSend').val(datesSelectedJSON);
			$('form.submission-form').submit();
		});
	});

});

// for lazy-loaded tables
var onTableReloadFinished = function () {
	$('.select-patient').click(function () {
		// get id and name
		patientId = $(this).attr('data-id');
		$('.patient-selected').html($(this).attr('data-name'));

		// open next page
		$('#page-one').slideUp(500);
		$('#page-two').slideDown(500);
	});
}