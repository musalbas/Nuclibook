var assignedStaff = [];
var assignedStaffNames = [];
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
            var dateOfBooking = $('.date-selector-output[data-sequence="2"]').val();
            var startTimeOfBooking = $('.time-selector-output[data-sequence="1"]').val();
            var endTimeOfBooking = $('.time-selector-output[data-sequence="2"]').val();
            if (startTimeOfBooking == "" || endTimeOfBooking == ""){
                toastr.error("You did not select the times of the booking");
            } else if (startTimeOfBooking > endTimeOfBooking) {
                toastr.error("Hours are not properly selected.");
            } else if (dateOfBooking=="") {
                toastr.error("Please select a date");
            } else if (new Date(dateOfBooking).getTime() < (new Date()).getTime()) {
                toastr.error("You cannot make a booking in the past");
            } else {
                addAssignedBooking(dateOfBooking, startTimeOfBooking, endTimeOfBooking);
            }
        });

//Pre-fill modal
        // notes
        var notes = $('#notes-all').val();
        if (notes == "None") {
            $('#notes-all').val("None");
        }
        // camera
        var currentCamera = $('#current-camera').val();
        $("#camera-select").val(currentCamera).attr('selected','selected');

        //tracer
        var currentTracer = $('#current-tracer').val();
        $("#tracer-select").val(currentTracer).attr('selected','selected');

        //staff
        var currentStaff = $('#current-staff').val();
        var currentStaffId = $('#current-staff-id').val();
        if (currentStaff == "<em>None</em>") {
            var staffArray = [];
            var staffIdArray = [];
        } else {
            var staffArray = currentStaff.split(", ");
            var staffIdArray = currentStaffId.split(", ");
        }
        // add staff to output
        for (var i in staffArray) {
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
            assignedStaffNames.push(staffArray[i]);

            // set up removal button
            $('.remove-assigned-staff').unbind('click').click(function (e) {
                e.preventDefault();
                var sId = $(this).attr('data-id');
                removeAssignedStaff(sId);
            });
        }

        //booking sections
        var bookingDate ="";
        var bookingTimers = $('#current-bookings-timers').val();
        var bookingTimersArray = bookingTimers.split(", ");
        for (var i in bookingTimersArray) {
            var startTime = bookingTimersArray[i].substring(10,15);
            var endTime = bookingTimersArray[i].substring(15);
            bookingDate = bookingTimersArray[i].substring(0, 10);
            var stringDate = bookingDate + startTime + endTime;

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
            + " <a class=\"remove-assigned-staff\" data-id=\"" + staffId + "\" href=\"javascript:;\"> [remove]</a>"
            + "</br></span>"
        );

        // add to list
        assignedStaff.push(staffId);
        assignedStaffNames.push(staffName);

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
        var newAssignedStaffNames = [];
        for (var i in assignedStaff) {
            if (assignedStaff[i] != staffId) {
                newAssignedStaff.push(assignedStaff[i]);
                newAssignedStaffNames.push(assignedStaffNames[i]);
            }
        }

        assignedStaff = newAssignedStaff;
        assignedStaffNames = newAssignedStaffNames;
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
            + " <a class=\"remove-assigned-booking\" data-id=\"" + stringDate + "\" href=\"javascript:;\"> [remove]</a>"
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
            if (assignedBooking[i] != bookingId) {
                newAssignedBooking.push(assignedBooking[i]);
            }
        }
        assignedBooking = newAssignedBooking;
    }

    $('.btn-save').click(function() {
        // add Staff to the hidden input field
        var stringStaffList = "";
        var stringStafListNames = "";
        for (var i in assignedStaff) {
            stringStaffList += assignedStaff[i] + ", ";
        }
        for (var i in assignedStaffNames) {
            stringStafListNames += assignedStaffNames[i] + ", ";
        }
        if (stringStaffList == "") {
            stringStaffList =  "<em>None</em>";
            stringStafListNames = "<em>None</em>";

        } else {
            stringStaffList = stringStaffList.substring(0, stringStaffList.length-2);
            stringStafListNames = stringStafListNames.substring(0, stringStafListNames.length-2);

        }
        $('#current-staff-id').val(stringStaffList);
        $('#current-staff').val(stringStafListNames);


        // add booking to the hidden field bookingSections
        var stringDatetoSent = "";
        for (var i in assignedBooking) {
            stringDatetoSent += assignedBooking[i] + ", ";
        }
        stringDatetoSent = stringDatetoSent.substring(0, stringDatetoSent.length-2);
        $('#current-bookings-timers').val(stringDatetoSent);

        if ($('#notes-all').val() == "") {
            $('#notes-all').val("<em>None</em>");
        }
        //Fields verification
        var tracerDose = $('input[name=tracer-dose]').val();
        var tracerOrderDate = $('input[name=tracer-order-due]').val();
        var tracerOrderNeeded = !$('input[name=no-tracer-order]').is(':checked');
        var staffAssigned = assignedStaff;

        // start validation
        var failedValidation = false;


        // validate tracer dose
        if (tracerDose.length == 0) {
            toastr.error("Please enter a tracer dose.");
            failedValidation = true;
        }

        // fail?
        if (failedValidation) return;

        // off we go!
       $('.edit-booking-form').submit();
    });
});