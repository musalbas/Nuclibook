$(document).ready(function () {

    $('#select').click(function () {

        $('.page-one').slideUp(500);

        $('#page-two').show();
    })
    $('#go-back-page-one').click(function () {
        $('.page-one').show();

        $('#page-two').hide();
    })

    $('#bookTherapy').click(function () {
        $('#page-two').slideUp(500);

        $('#page-three').show();
    })

    $('#selectTherapy').click(function () {
        $('#page-three').slideUp(500);

        $('#page-four').show();
    })

    $('#go-back-page-three').click(function () {

        $('#page-three').show();

        $('#page-four').hide();

    });


    $('#viewAvailableAppointments').click(function () {
        $('#page-four').slideUp(500);

        $('.calendar').show().fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable: true
        })
    });

    // datatables
    setUpDataTable('#patients-table', 0, [[1, 1], [1, 1], [1, 1], [0, 0]]);
});