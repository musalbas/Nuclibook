$(document).ready(function () {
// Adding years to the select list up tu current years starting from 1920
    var i = 1920;
    while (i <= (new Date).getFullYear()) {
        $('#year-of-birth').append('<option value="'+i+'">' + i + '</option>');
        i++;
    }
    $('#month-of-birth').append('<option value="01">January</option>');
    $('#month-of-birth').append('<option value="02">February</option>');
    $('#month-of-birth').append('<option value="03">March</option>');
    $('#month-of-birth').append('<option value="04">April</option>');
    $('#month-of-birth').append('<option value="05">May</option>');
    $('#month-of-birth').append('<option value="06">June</option>');
    $('#month-of-birth').append('<option value="07">July</option>');
    $('#month-of-birth').append('<option value="08">August</option>');
    $('#month-of-birth').append('<option value="09">September</option>');
    $('#month-of-birth').append('<option value="10">October</option>');
    $('#month-of-birth').append('<option value="11">November</option>');
    $('#month-of-birth').append('<option value="12">December</option>');

    $('#year-of-birth').click(function () {
        var selectedYear = $('#year-of-birth option:selected').text();
        if ($.isNumeric(selectedYear)) {
            $('#month-of-birth').removeAttr('disabled');

            $('#day-of-birth').attr('disabled','');
            $('#day-of-birth').empty();
            $('#day-of-birth').append('<option disabled selected>Day</option>');
        }
    });

    $('#month-of-birth').click(function () {
        //Starting to open days checking what month and year is
        var selectedYear = parseInt($('#year-of-birth option:selected').text(), 10);
        var selectedMonth = $('#month-of-birth option:selected').text();
        var monthsArr = ['January', 'March', 'May', 'July', 'August', 'October', 'December'];
        $('#day-of-birth').empty();
        $('#day-of-birth').append('<option disabled selected>Day</option>');

        if ($.inArray(selectedMonth, monthsArr) > -1) {
            //console.log("este luna de 31");
            var i = 1;
            while (i < 32) {
                $('#day-of-birth').append('<option value="' + i+ '">' + i + '</option>');
                ++i;
            }
        } else {
            //console.log("nu este luna de 31");
            if (selectedMonth == "February") {
                if (((selectedYear % 4 == 0) && (selectedYear % 100 != 0)) || (selectedYear % 400 == 0)) {
                    var i = 1;
                    while (i < 30) {
                        $('#day-of-birth').append('<option value="' + i+ '">' + i + '</option>');
                        ++i;
                    }
                } else {
                    var i = 1;
                    while (i < 29) {
                        $('#day-of-birth').append('<option value="' + i+ '">' + i + '</option>');
                        ++i;
                    }
                }
            } else {
                var i = 1;
                while (i < 31) {
                    $('#day-of-birth').append('<option value="' + i+ '">' + i + '</option>');
                    ++i;
                }
            }
        }
        if (selectedMonth != "Month") {
            $('#day-of-birth').removeAttr('disabled');
        }
    });
});