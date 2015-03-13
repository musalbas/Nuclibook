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

    //geting Sunday Saturday Dates to pass to CalendarRoute
    var currDate = (new Date().getDate());
    var currDay = (new Date().getDay());
    var currMonth = (new Date().getMonth()) + 1;
    var currYear = (new Date().getFullYear());

    var sundayString = currYear + '-' + (currMonth < 10 ? '0' : '') + currMonth + '-' + ((currDate - currDay) < 10 ? '0' : '') + (currDate - currDay);
    var todayString = currYear + '-' + (currMonth < 10 ? '0' : '') + currMonth + '-' + ((currDate - currDay) < 10 ? '0' : '') + currDate;
    var saturdayString = currYear + '-' + (currMonth < 10 ? '0' : '') + currMonth + '-' + ((currDate - (6-currDay)) < 10 ? '0' : '') +(currDate + (6 - currDay));
    console.log("Current day : " + currDay + " Monday: " + sundayString + " Today : " + todayString + " Friday: " + saturdayString);

    $('#view-available-appointments').click(function () {
        // hide buttons
        $(this).slideUp(300);
        $('#go-back-to-select-therapy').hide();


        // show calendar
        var appointmentsArray;
        var calendar =
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

        // ajax for retrieving current booking sections
        //start=2014-03-13&end=...

        $.get('/calendar?start=' + sundayString + '&end=' + saturdayString)
            .done(function (result) {
                var tryharder = result.toString();
                var jsonTestForm = JSON.parse(tryharder);

                var appointmentsArray = [];
                for (var i = 0; i < jsonTestForm.week.length; ++i) {
                    for (var j = 0; j < jsonTestForm.week[i].bookings.length; ++j) {
                        for (var k = 0; k < jsonTestForm.week[i].bookings[j].bookingSections.length; ++k) {
                            var title = "Patient: " + jsonTestForm.week[i].bookings[j].patientId + " Therapy: " + jsonTestForm.week[i].bookings[j].therapyName;
                            var start = jsonTestForm.week[i].day + "T" + jsonTestForm.week[i].bookings[j].bookingSections[k].startTime;
                            var end = jsonTestForm.week[i].day + "T" + jsonTestForm.week[i].bookings[j].bookingSections[k].endTime;
                            appointmentsArray.push({title: title, start: start, end: end, allDay: false});
                        }
                    }
                }
            }
        ).fail(function (xhr, textStatus, errorThrown) {
                console.log(errorThrown);
                console.log("failed to retrieve bookings");
            }
        );
    });

    // datatables
    setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
    setUpDataTable('#therapies-table', 0, [[1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [0, 0]]);
});
