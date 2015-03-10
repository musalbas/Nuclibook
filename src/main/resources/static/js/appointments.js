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

        var calendar =
            $('.calendar').show().fullCalendar({
                header: {

                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay'

                },

                defaultView: 'agendaWeek',
                editable: true,
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

                events: [
                    {
                        title: 'My Event',

                        start: '2015-03-10T14:30:00',
                        end: '2015-03-10T16:30:00',
                        allDay: false
                    }
                    // other events here...
                ],
                timeFormat: 'H(:mm)'

            });

        // ajax for retrieving current booking sections
        $.post(
            '/calendar',
            { start_date: new Date().getDate() }
        ).done(function (result) {
                console.log(result);
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