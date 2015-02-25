/**
 * Created by Fares Alaboud on 24/02/2015.
 */

function keepFocus() {
    var focusedElement = $(stage == 2 ? '#password' : '#username');

    focusedElement.focus();

    focusedElement.unbind("blur").blur(function () {
        setTimeout(function () {
            focusedElement.focus();
        }, 0);
    });
}

$(document).ready(function() {
   keepFocus();
});

$(window).focus(function() {
   keepFocus();
});