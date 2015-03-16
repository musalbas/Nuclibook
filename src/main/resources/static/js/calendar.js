var calendarEvents = [];

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
		},

		// on-select function
		select: onSelect,

		// pop-up with details
		eventRender: function (event, element) {
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
		}
	});

	// add loading icon
	cal.find('.fc-header-right').html('<span class="calendar-loading-msg"><img src="/images/loading.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;<strong>Loading...</strong>&nbsp;&nbsp;&nbsp;&nbsp;</span>');

	return cal;
}

function updateCalendar(selector, startDate, endDate, options) {
	// show loading message
	$('.calendar-loading-msg').show();

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
	for (var key in options) {
		url += '&' + key + '=' + options[key];
	}

	// send AJAX call
	$.get(url)
		.done(function (result) {
			var rawJson = result.toString();
			var parsedJson = JSON.parse(rawJson);

			// loop through bookings
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
						+ "<br>" + +bookingCameraType,
						allDay: false
					});
				}
			}
			selector.fullCalendar('refetchEvents');

			// hide loading message
			$('.calendar-loading-msg').hide();
		})
		.fail(function (xhr, textStatus, errorThrown) {
			toastr.error("Failed to load calendar data.")
			$('.calendar-loading-msg').hide();
		}
	);
}
