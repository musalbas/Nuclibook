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

    var currentDateDay = (new Date).getDate();
    var currentDateMonth = (new Date).getMonth();
    var currentDateYear = (new Date).getFullYear();
    var updateCalendarWeek = function () {

    }

    var appointmentsArray = [];
    var calendar = $('.calendar');
    $('#view-available-appointments').click(function () {
        // hide buttons
        $(this).slideUp(300);
        $('#go-back-to-select-therapy').hide();

        // show calendar
        calendar =
            $('.calendar').show().fullCalendar({
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
                    appointmentsArray.length = 0;
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
                timeFormat: 'H(:mm)',
                weekends: true,
                slotMinutes: 15

            });
    });


    function callAjax(startDate, endDate) {

       // calendar.fullCalendar('removeEventSource');

        //geting Sunday Saturday Dates to pass to CalendarRoute
        var sundayString = startDate.getFullYear() + '-' + (startDate.getMonth() < 10 ? '0' : '') + (startDate.getMonth() + 1) + '-' + ((startDate.getDate()) < 10 ? '0' : '') + (startDate.getDate());
        endDate = new Date(((endDate.getTime()) - 86400000));
        var saturdayString = endDate.getFullYear() + '-' + (endDate.getMonth() < 10 ? '0' : '') + (endDate.getMonth() + 1) + '-' + ((endDate.getDate()) < 10 ? '0' : '') + (endDate.getDate());
        $.get('/calendar?start=' + sundayString + '&end=' + saturdayString)
            .done(function (result) {
                var tryharder = result.toString();
                var jsonTestForm = JSON.parse(tryharder);
                for (var i = 0; i < jsonTestForm.bookings.length; ++i) {
                    //Start
                    for (var j = 0; j < jsonTestForm.bookings[i].bookingSections.length; ++j) {
                        var title = "Patient: " + jsonTestForm.bookings[i].patientName + "\nTherapy: " + jsonTestForm.bookings[i].therapyName;
                        var start = jsonTestForm.bookings[i].bookingSections[j].startTime + ":00";
                        start = start.replace(" ", "T");
                        var end = jsonTestForm.bookings[i].bookingSections[j].endTime + ":00";
                        end = end.replace(" ", "T");
                        appointmentsArray.push({title: title, start: start, end: end, allDay: false});
                    }
                }
                calendar.fullCalendar('refetchEvents');

                console.log(appointmentsArray);


            }
        ).fail(function (xhr, textStatus, errorThrown) {
                console.log(errorThrown);
                console.log("failed to retrieve bookings");
            }
        );
    }

// datatables
    setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
    setUpDataTable('#therapies-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);
})
;
