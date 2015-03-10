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

    $('#view-available-appointments').click(function () {
        // hide buttons
        $(this).slideUp(300);
        $('#go-back-to-select-therapy').hide();

        // ajax for retrieving current booking sections
        $.get('/calendar?start=' + new Date())
            .done(function (result) {
                console.log("Day: " + currentDateDay + " Month: " + (currentDateMonth + 1) + " Year: " + currentDateYear);

                var tryharder = result.toString();
                var tryjson = JSON.parse(tryharder);

                var jsonTestString = '{"week" :[ { "day": "2015-03-10", "bookings": [ { "patientId": "0000000", "therapyName": "therapy A", "bookingSections": [ { "startTime": "09:30", "endTime": "11:50" }, { "startTime": "14:00", "endTime": "16:55" } ]}, { "patientId": "8888888", "therapyName": "therapy B", "bookingSections": [ { "startTime": "17:30", "endTime": "19:30" }, { "startTime": "20:00", "endTime": "21:00" }, { "startTime": "21:00", "endTime": "21:30" } ]} ]}, { "day": "2015-03-13", "bookings": [ { "patientId": "0000000", "therapyName": "therapy A", "bookingSections": [ { "startTime": "09:30", "endTime": "11:00" }, { "startTime": "12:00", "endTime": "12:30" } ]}, { "patientId": "8888888", "therapyName": "therapy B", "bookingSections": [ { "startTime": "13:30", "endTime": "17:40" }, { "startTime": "17:50", "endTime": "18:50" }, { "startTime": "19:00", "endTime": "21:20" } ]} ]} ]}';
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
                // show calendar

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
                        weekends: false,
                        slotMinutes: 15

                    });


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
