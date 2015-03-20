var assignedStaff = [];
var assignedBooking = [];

$(document).ready(function () {
    $("#edit-button-modal").click(function() {
        $('.booking-edit-modal').removeClass('hide').modal('show');
    });
    createModal();

    function createModal() {
        // set up the tracer order date picker
        prepareDateSelector();
        prepareTimeSelector();

        // set listener for "no tracer order"
        $('#no-tracer-order').change(function (e) {
            if ($(this).is(':checked')) {
                $('.tracer-order-wrapper').slideUp(200);
            } else {
                $('.tracer-order-wrapper').slideDown(200);
            }
        });

        // set up staff adding
        $('.add-staff-member').click(function (e) {
            var selectedOption = $('#staff').find('option:selected');
            var staffId = selectedOption.val();
            var staffName = selectedOption.text();
            addAssignedStaff(staffId, staffName);
        });

        //set up booking section adding
        $('.add-booking-section').click(function (e) {
            //var selectedOption = $('#staff').find('option:selected');
            //var staffId = selectedOption.val();
            //var staffName = selectedOption.text();
            var dateOfBooking = $('.date-selector-output[data-sequence="2"]').val();
            var startTimeOfBooking = $('.time-selector-output[data-sequence="1"]').val();
            var endTimeOfBooking = $('.time-selector-output[data-sequence="2"]').val();
            if (startTimeOfBooking == "" || endTimeOfBooking == ""){
                toastr.error("TO BE COMPLETED Please select the times");
            } else if (startTimeOfBooking > endTimeOfBooking) {
                toastr.error("TO BE COMPLETED Hours not ok");
            } else if (dateOfBooking=="") {
                toastr.error("Please select a date");
            } else if (new Date(dateOfBooking).getTime() < (new Date()).getTime()) {
                toastr.error("You cannot make a booking in the past");
            } else {
                addAssignedBooking(dateOfBooking, startTimeOfBooking, endTimeOfBooking);
            }
        });

//Pre-fill modal
        // camera
        var currentCamera = $('#current-camera').val();
        $("#camera-select").val(currentCamera).attr('selected','selected');

        //tracer
        var currentTracer = $('#current-tracer').val();
        $("#tracer-select").val(currentTracer).attr('selected','selected');

        //staff
        var currentStaff = $('#current-staff').val();
        var currentStaffId = $('#current-staff-id').val();
        var staffArray = currentStaff.split(", ");
        var staffIdArray = currentStaffId.split(", ");
        // add staff to output
        for (var i in staffArray) {
            console.log(staffIdArray[i] + " " + staffArray[i]);
            var assignedStaffOutput = $('.assigned-staff');
            assignedStaffOutput.html(
                assignedStaffOutput.html()
                + "<span class=\"assigned-staff-" + staffIdArray[i] + "\">"
                + staffArray[i]
                + " <a class=\"remove-assigned-staff\" data-id=\"" + staffIdArray[i] + "\" href=\"javascript:;\">[remove]</a>"
                + "</br></span>"
            );

            // add to list
            assignedStaff.push(staffIdArray[i]);

            // set up removal button
            $('.remove-assigned-staff').unbind('click').click(function (e) {
                e.preventDefault();
                var sId = $(this).attr('data-id');
                removeAssignedStaff(sId);
            });
        }

        //booking sections
        var bookingDate = $('#current-bookings-date').val();
        var bookingTimers = $('#current-bookings-timers').val();
        var bookingTimersArray = bookingTimers.split(", ");
        console.log(bookingTimersArray);
        for (var i in bookingTimersArray) {
            var startTime = bookingTimersArray[i].split(" - ")[0];
            var endTime = bookingTimersArray[i].split(" - ")[1];
            var stringDate = bookingDate + startTime + endTime;
            for (var i in assignedBooking) {
                if (assignedBooking[i] == stringDate ) {
                    toastr.error("<b>" + bookingDate + "</b>" + " @ " + startTime + " - " + endTime + " is already assigned.");
                    return;
                }
            }

            // add to output
            var assignedBookingOutput = $('.assigned-booking-section');
            assignedBookingOutput.html(
                assignedBookingOutput.html()
                + "<span class=\"assigned-booking-section-" + stringDate + "\">"
                + "<b>" + bookingDate + "</b>" + " @ " + startTime + " - " + endTime
                + "<a class=\"remove-assigned-booking\" data-id=\"" + stringDate + "\" href=\"javascript:;\">[remove]</a>"
                + "</br></span>"
            );

            // add to list
            assignedBooking.push(stringDate);

            // set up removal button
            $('.remove-assigned-booking').unbind('click').click(function (e) {
                e.preventDefault();
                var sId = $(this).attr('data-id');
                removeBookingSection(sId, this);
            });
        }
    }
    /*

     */
    function addAssignedStaff(staffId, staffName) {
        // default?
        if (staffId == 0) return;

        // already in list?
        for (var i in assignedStaff) {
            if (assignedStaff[i] == staffId) {
                toastr.error(staffName + " is already assigned.");
                return;
            }
        }

        // add to output
        var assignedStaffOutput = $('.assigned-staff');
        assignedStaffOutput.html(
            assignedStaffOutput.html()
            + "<span class=\"assigned-staff-" + staffId + "\">"
            + staffName
            + " <a class=\"remove-assigned-staff\" data-id=\"" + staffId + "\" href=\"javascript:;\">[remove]</a>"
            + "</br></span>"
        );

        // add to list
        assignedStaff.push(staffId);

        // set up removal button
        $('.remove-assigned-staff').unbind('click').click(function (e) {
            e.preventDefault();
            var sId = $(this).attr('data-id');
            removeAssignedStaff(sId);
        });
    }

    function removeAssignedStaff(staffId) {
        // remove from HTML
        $('span.assigned-staff-' + staffId).remove();

        // remove from array
        var newAssignedStaff = [];
        for (var i in assignedStaff) {
            if (assignedStaff[i] != staffId) newAssignedStaff.push(assignedStaff[i]);
        }
        assignedStaff = newAssignedStaff;
    }

    //Add booking
    function addAssignedBooking(date, startTime, endTime) {
        // already in list?
        var stringDate = date + startTime + endTime;
        for (var i in assignedBooking) {
            if (assignedBooking[i] == stringDate ) {
                toastr.error("<b>" + date + "</b>" + " @ " + startTime + " - " + endTime + " is already assigned.");
                return;
            }
        }

        // add to output
        var assignedBookingOutput = $('.assigned-booking-section');
        assignedBookingOutput.html(
            assignedBookingOutput.html()
            + "<span class=\"assigned-booking-section-" + stringDate + "\">"
            + "<b>" + date + "</b>" + " @ " + startTime + " - " + endTime
            + " <a class=\"remove-assigned-booking\" data-id=\"" + stringDate + "\" href=\"javascript:;\">[remove]</a>"
            + "</br></span>"
        );

        // add to list
        assignedBooking.push(stringDate);

        // set up removal button
        $('.remove-assigned-booking').unbind('click').click(function (e) {
            e.preventDefault();
            var sId = $(this).attr('data-id');
            removeBookingSection(sId, this);
        });
    }

    function removeBookingSection(bookingId, elem) {
        // remove from HTML
        $(elem).parent().remove();

        // remove from array
        var newAssignedBooking = [];
        for (var i in assignedBooking) {
            if (assignedBooking[i] != bookingId) newAssignedBooking.push(assignedBooking[i]);
        }
        assignedBooking = newAssignedBooking;
    }

    $('.btn-save').click(function() {
        alert("Ok clicked. Watch out ");
        var jsonToSend = "";
        var cameraId = $('select[name=camera]').val();
        var tracerId = $('select[name=tracer]').val();
        var tracerDose = $('input[name=tracer-dose]').val();
        //var tracerOrderDate = $('input[name=tracer-order-due]').val();
        //var tracerOrderNeeded = !$('input[name=no-tracer-order]').is(':checked');
        var staffAssigned = assignedStaff;

        //// start validation
        //var failedValidation = false;
        //
        //// validate camera ID
        //if (!$.isNumeric(cameraId)) {
        //    toastr.error("Please select a camera.");
        //    failedValidation = true;
        //}
        //
        //// validate tracer ID
        //if (!$.isNumeric(tracerId)) {
        //    toastr.error("Please select a tracer.");
        //    failedValidation = true;
        //}
        //
        //// validate tracer dose
        //if (tracerDose.length == 0) {
        //    toastr.error("Please enter a tracer dose.");
        //    failedValidation = true;
        //}
        //
        //// validate tracer order date
        //if (tracerOrderNeeded && !tracerOrderDate.match(/[0-9]{4}\-[0-9]{2}\-[0-9]{2}/)) {
        //    toastr.error("Please select a tracer order date.");
        //    failedValidation = true;
        //}
        //
        //// fail?
        //if (failedValidation) return;

        // fill in necessary parts of form
        $('input[name=assigned-staff]').val(staffAssigned.join(','));

        // off we go!
        $('.edit-booking-form').submit();
    });
});