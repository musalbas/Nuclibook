/**
 * Created by GEORGE RADUTA on 19.02.2015.
 */

//$(".btn-cancel").click(function() {
//    console.log("Canceled");
//    //Geting the value from duration input. Will use Mark's formObject.duration after connections are done
//    var number = $("#duration").val();
//    if ($.isNumeric(number) == false) {
//        console.log("mata");
//        toastr.error("You did not enter a valid number");
//    }
//});
var validateCreateForm = function (formObject) {
    var error = false;
    // check name
    if (formObject.name.length < 1) {
        toastr.error("You did not enter a valid name");
        error = true;
    }

    // check if the user inserted a number
    var number = formObject.duration;
    if ($.isNumeric(number) == false) {
        toastr.error("You did not enter a valid number");
    }

    return !error;
};

// toastr options
toastr.options = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-top-right",
    "onclick": null,
    "showDuration": "1000",
    "hideDuration": "1000",
    "timeOut": "4000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};