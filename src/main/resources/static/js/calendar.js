var calendarEvents = [];

var currentOptions = {};

var selectedCameras = null;

var calendarChannelOptions = {
	bookings: true,
	staffAbsences: true
};

function setupCalendar(selector, onSelect, viewOptions) {
	var cal = selector.show().fullCalendar({
		// basic appearance
		header: {
			left: 'prev,next today',
			center: 'title',
			right: ''
		},
		defaultView: 'agendaWeek',
		selectable: true,
		selectHelper: true,
		allDaySlot: false,
		timeFormat: 'HH:mm',
		weekends: true,
		slotMinutes: 15,

		// set data source
		events: calendarEvents,

		// TODO
		minTime: "08:00:00",
		maxTime: "19:00:00",

		// what to display?
		viewDisplay: function (e) {
			// clear bookings
			calendarEvents.length = 0;

			// reload for new date range
			updateCalendar(selector, e.start, e.end, viewOptions);

			// resize
			var v = this;
			e.setHeight($(window).height() - $(v).position().top - 60);
			setTimeout(function () {
				e.setHeight($(window).height() - $(v).position().top - 60);
			}, 500);
		},

		// on-select function
		select: onSelect,

		// pop-up with details
		eventRender: function (event, element) {
			element.addClass(event.type);

			element.popover({
				title: event.title,
				placement: 'auto',
				html: true,
				trigger: 'hover',
				animation: 'true',
				content: event.msg,
				container: 'body'
			});

			// click listener to hide
			$('body').on('click', function (e) {
				if (!element.is(e.target) && element.has(e.target).length === 0
					&& $('.popover').has(e.target).length === 0)
					element.popover('hide');
			});
		},

		eventClick: function (calEvent, jsEvent, view) {
			if (calEvent.url && viewOptions['linkEvents']) {
				window.location.href = calEvent.url;
			}
			return false;
		},

		windowResize: function (calView) {
			calView.setHeight($(window).height() - $(this).position().top - 60);
		}
	});

	// add loading icon and toggle buttons
	cal.find('.fc-header-right').html('' +
	'<span class="calendar-loading-msg">' +
	'<img src="/images/loading.gif"/>' +
	'&nbsp;&nbsp;&nbsp;&nbsp;' +
	'<strong>Loading...</strong>' +
	'&nbsp;&nbsp;&nbsp;&nbsp;' +
	'</span>' +
	'<span class="calendar-channel-filters hide">' +
	'<button class="btn btn-default calendar-channel-toggle" data-target="staffAbsences"><i class="fa fa-fw fa-check-square-o"></i> Absences</button>' +
	'&nbsp;&nbsp;' +
	'<button class="btn btn-default calendar-channel-toggle" data-target="genericEvents"><i class="fa fa-fw fa-check-square-o"></i> Events</button>' +
	'&nbsp;&nbsp;' +
	'<button class="btn btn-default calendar-channel-cameras"><i class="fa fa-fw fa-edit"></i> Bookings</button>' +
	'</span>');

	// perform action for toggle buttons
	$('.calendar-channel-toggle').click(function (e) {
		e.preventDefault();

		// get target
		var target = $(this).data('target');

		// switch button classes
		if (calendarChannelOptions[target]) {
			$(this).find('i').removeClass('fa-check-square-o').addClass('fa-square-o');
		} else {
			$(this).find('i').removeClass('fa-square-o').addClass('fa-check-square-o');
		}

		// change option
		calendarChannelOptions[target] = !calendarChannelOptions[target];

		// redraw
		calendarEvents.length = 0;
		updateCalendar(
			currentOptions['selector'],
			currentOptions['startDate'],
			currentOptions['endDate'],
			currentOptions['options']
		);

		// drop focus
		$(this).blur();
	});

	// perform action for camera selection buttons
	$('.calendar-channel-cameras').click(function (e) {
		e.preventDefault();
		openCameraSelectModal();
	});

	return cal;
}

function updateCalendar(selector, startDate, endDate, options) {
	// store current options
	currentOptions['selector'] = selector;
	currentOptions['startDate'] = startDate;
	currentOptions['endDate'] = endDate;
	currentOptions['options'] = options;

	// show loading message
	$('.calendar-loading-msg').show();
	$('.calendar-channel-filters').hide();

	// adjust end date backwards by one
	endDate = new Date(((endDate.getTime()) - 86400000));

	// get start/end strings to pass to calendar data route
	var startDateString = startDate.getFullYear()
		+ '-'
		+ (startDate.getMonth() < 10 ? '0' : '') + (startDate.getMonth() + 1)
		+ '-'
		+ ((startDate.getDate()) < 10 ? '0' : '') + (startDate.getDate());
	var endDateString = endDate.getFullYear()
		+ '-'
		+ (endDate.getMonth() < 10 ? '0' : '') + (endDate.getMonth() + 1)
		+ '-'
		+ ((endDate.getDate()) < 10 ? '0' : '') + (endDate.getDate());

	// build URL
	var url = '/calendar-data?start=' + startDateString + '&end=' + endDateString;
	for (var key in options) url += '&' + key + '=' + (options[key] === true ? '1' : options[key]);
	for (key in calendarChannelOptions) url += '&' + key + '=' + (calendarChannelOptions[key] === true ? '1' : calendarChannelOptions[key]);
	url += '&cameras=';
	if (selectedCameras == null) {
		// default to all
		url += 'all';
	} else {
		url += selectedCameras.join(',');
	}

	// send AJAX call
	$.get(url)
		.done(function (result) {
			var rawJson = result.toString();
			var parsedJson = JSON.parse(rawJson);

			// loop through bookings
			if (calendarChannelOptions['bookings'] && typeof(parsedJson.bookings) != "undefined") {
				var bookingTitle, bookingCameraType, bookingStart, bookingEnd;
				for (var i = 0; i < parsedJson.bookings.length; ++i) {
					for (var j = 0; j < parsedJson.bookings[i].bookingSections.length; ++j) {
						// build title
						bookingTitle = parsedJson.bookings[i].therapyName + ":\n" + parsedJson.bookings[i].patientName;

						// build camera type
						bookingCameraType = parsedJson.bookings[i].cameraName;

						// start and end time
						bookingStart = parsedJson.bookings[i].bookingSections[j].startTime + ":00";
						bookingStart = bookingStart.replace(" ", "T");
						bookingEnd = parsedJson.bookings[i].bookingSections[j].endTime + ":00";
						bookingEnd = bookingEnd.replace(" ", "T");

						// add event
						calendarEvents.push({
							title: bookingTitle,
							start: bookingStart,
							end: bookingEnd,
							msg: "" +
							"Start time: <strong>" + parsedJson.bookings[i].bookingSections[j].startTime.substring(10, 16) + "</strong>"
							+ "<br/>" +
							"End time: <strong>" + parsedJson.bookings[i].bookingSections[j].endTime.substring(10, 16) + "</strong>"
							+ "<br>" + bookingCameraType,
							allDay: false,
							url: '/booking-details/' + parsedJson.bookings[i].id,
							type: 'booking booking-' + parsedJson.bookings[i].colourNumber
						});
					}
				}
			}

			// loop through absences
			if (calendarChannelOptions['staffAbsences'] && typeof(parsedJson.staffAbsences) != "undefined") {
				var absenceTitle, absenceStart, absenceEnd;
				for (i = 0; i < parsedJson.staffAbsences.length; ++i) {
					// build title
					absenceTitle = "Absent: " + parsedJson.staffAbsences[i].staffName;

					// start and end time
					absenceStart = parsedJson.staffAbsences[i].from + ":00";
					absenceStart = absenceStart.replace(" ", "T");
					absenceEnd = parsedJson.staffAbsences[i].to + ":00";
					absenceEnd = absenceEnd.replace(" ", "T");

					// add event
					calendarEvents.push({
						title: absenceTitle,
						start: absenceStart,
						end: absenceEnd,
						msg: "" +
						"Start time: <strong>" + parsedJson.staffAbsences[i].from.substring(10, 16) + "</strong>" +
						"<br/>" +
						"End time: <strong>" + parsedJson.staffAbsences[i].to.substring(10, 16) + "</strong>",
						allDay: false,
						type: 'absence'
					});
				}
			}

			// update events
			selector.fullCalendar('refetchEvents');

			// hide loading message
			$('.calendar-loading-msg').hide();
			$('.calendar-channel-filters').removeClass('hide').show();
		})
		.fail(function (xhr, textStatus, errorThrown) {
			toastr.error("Failed to load calendar data.")
			$('.calendar-loading-msg').hide();
		}
	);
}

function openCameraSelectModal() {
	var modal = $('.camera-select-modal');

	modal.removeClass('hide').modal({
		backdrop: 'static',
		keyboard: false
	});

	$('.btn-update').unbind('click').click(function (e) {
		// get selected
		selectedCameras = $('.selected-cameras:checked').map(function () {
			return this.value;
		}).get();

		// redraw
		calendarEvents.length = 0;
		updateCalendar(
			currentOptions['selector'],
			currentOptions['startDate'],
			currentOptions['endDate'],
			currentOptions['options']
		);

		// close modal
		modal.modal('hide');
	});

	$('.btn-cancel').unbind('click').click(function (e) {
		modal.modal('hide');
	});
}