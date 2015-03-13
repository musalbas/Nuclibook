var patientId = 0;
var therapyId = 0;

$(document).ready(function () {

    /*********************
     FIRST STAGE NAVIGATION
     **********************/

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

    /***********************
     FIRST STAGE DATA TABLES
     ***********************/

    setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
    setUpDataTable('#therapies-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);

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
    var appointmentsArray = [];
    var calendar = $('.calendar');

    // when the "show appointments" button is clicked...
    $('#view-available-appointments').click(function () {
        // hide buttons
        $(this).slideUp(300);
        $('#go-back-to-select-therapy').hide();

        // show calendar
        calendar = $('.calendar').show().fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaWeek'
            },

            defaultView: 'agendaWeek',
            selectable: true,
            selectHelper: true,
            minTime: "08:00:00",
            maxTime: "19:00:00",
            allDaySlot: false,

            viewDisplay: function (e) {
                // clear appointments
                appointmentsArray.length = 0;
                // reload for new date range
                callAjax(e.start, e.end);
            },

            select: function (start, end, allDay) {
                var title = prompt('Event Title:');

                if (title) {
                    calendar.fullCalendar('renderEvent',
                        {
                            title: title,
                            start: start,
                            end: end,
                            allDay: allDay
                        },
                        true // make the event "stick"
                    );
                }
                calendar.fullCalendar('unselect');
            },

            events: appointmentsArray,
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
                $('body').on('click', function (e) {
                    if (!element.is(e.target) && element.has(e.target).length === 0 && $('.popover').has(e.target).length === 0)
                        element.popover('hide');
                });
            },
            timeFormat: 'HH:mm',
            weekends: true,
            slotMinutes: 15
        });
    });


    function callAjax(startDate, endDate) {
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

        // send AJAX call
        $.get('/calendar?start=' + startDateString + '&end=' + endDateString)
            .done(function (result) {
                var rawJson = result.toString();
                var parsedJson = JSON.parse(rawJson);

                // loop through bookings
                var bookingTitle, bookingCameraType, bookingStart, bookingEnd;
                for (var i = 0; i < parsedJson.bookings.length; ++i) {
                    for (var j = 0; j < parsedJson.bookings[i].bookingSections.length; ++j) {
                        // build title
                        bookingTitle = parsedJson.bookings[i].therapyName + ":\n <b>" + parsedJson.bookings[i].patientName + "</b>";

                        //build Camera Type
                        bookingCameraType = parsedJson.bookings[i].cameraName;
                        // start and end time
                        bookingStart = parsedJson.bookings[i].bookingSections[j].startTime + ":00";
                        bookingStart = bookingStart.replace(" ", "T");
                        bookingEnd = parsedJson.bookings[i].bookingSections[j].endTime + ":00";
                        bookingEnd = bookingEnd.replace(" ", "T");

                        // add event
                        appointmentsArray.push({
                            title: bookingTitle,
                            start: bookingStart,
                            msg: "Start time: <b>"
                            + parsedJson.bookings[i].bookingSections[j].startTime.substring(10, 16)
                            + "</b><br> End time: <b>" + parsedJson.bookings[i].bookingSections[j].endTime.substring(10, 16)
                            + "<br>" + bookingCameraType + "</b>",
                            end: bookingEnd,
                            allDay: false
                        });
                    }
                }

                calendar.fullCalendar('refetchEvents');
            })
            .fail(function (xhr, textStatus, errorThrown) {
                console.log(errorThrown);
                console.log("failed to retrieve bookings");
            }
        );
    }
});