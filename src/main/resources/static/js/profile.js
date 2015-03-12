var validateCreateForm = function (formObject) {
    var error = false;

    // check password length
    if (formObject.password.length < 6) {
        toastr.error("Your password must be at least 6 characters long");
        error = true;
    }

    // check password match
    if (formObject.password != formObject.password_check) {
        toastr.error("Your passwords did not match");
        error = true;
    }

    return !error;
};

$(document).ready(function() {
    openPasswordModal();
});

var openPasswordModal = function() {
    $("#edit-password-button").trigger('click');
};