/**
 * Created by Fares Alaboud on 24/02/2015.
 */

function keepFocus(field) {
    var focusedElement = document.getElementById(field);

    focusedElement.focus();

    focusedElement.onblur = function () {
        setTimeout(function () {
            focusedElement.focus();
        });
    };
}

function submitForm() {
    var form = document.getElementById("login-form");

    document.getElementById("login-continue-button").addEventListener("click", function () {
        form.submit();
    });
}
