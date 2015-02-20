/**
 * Created by Navi on 20/02/2015.
 */



var validateCreateForm = function (formObject) {
    var error = false;

    if (formObject.permission.length < 1) {
        toastr.error("Please assign permission to role");
        error = true;
    }
        return !error
    }
