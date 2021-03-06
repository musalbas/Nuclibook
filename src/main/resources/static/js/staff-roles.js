$(document).ready(function () {
	// "show more" links
	$('.more-permissions').click(function (e) {
		e.preventDefault();
		$('#' + $(this).attr('data-target')).slideDown();
		$(this).closest('span').hide();
	})
});

var onFormLoadSetup = function() {
    $('.select-all').click(function() {
        if($(this).prop('checked')) {
            $('.crud-list-prefill-permission-ids').prop( "checked", true );
        }
        else {
            $('.crud-list-prefill-permission-ids').prop( "checked", false );
        }
    });
};

var validateCreateForm = function (formObject) {
	var error = false;

    var roleTitleString = formObject["label"];

    // Check number of title characters to be in interval [1,32]
    if (roleTitleString.trim().length < 1) {
        toastr.error("You did not enter a valid role title.");
        error = true;
    }
    if (roleTitleString.length > 32) {
        toastr.error("Role title should not exceed 32 characters.");
        error = true;
    }

    if(!(/^[A-Za-z\d\s]+$/.test(roleTitleString))){
        toastr.error("Role title should only contain numbers, letters and spaces");
        error = true;
    }

    if(!findNumOfSelectedCheckboxes(formObject)){
        toastr.error("Please select at least one permission");
        error = true;
    }

	return !error;
};

var validateEditForm = function (formObject) {
    var error = false;

    var roleTitleString = formObject["label"];

    // Check number of title characters to be in interval [1,32]
    if (roleTitleString.trim().length < 1) {
        toastr.error("You did not enter a valid role title.");
        error = true;
    }
    if (roleTitleString.length > 32) {
        toastr.error("Role title should not exceed 32 characters.");
        error = true;
    }

    if(!(/^[A-Za-z\d\s]+$/.test(roleTitleString))){
        toastr.error("Role title should only contain numbers, letters and spaces");
        error = true;
    }

    if(!findNumOfSelectedCheckboxes(formObject)){
        toastr.error("Please select at least one permission");
        error = true;
    }

    return !error;
};

var findNumOfSelectedCheckboxes = function(formObject) {
    var matches = 0;
    var key;
    for (key in formObject) {
        if (key.match('^permission-')) {
            matches++;
        }
    }
    return matches;
};

$(document).ready(function () {
	setUpDataTable('#staff-roles-table', [[1, 1], [0, 1], [0, 0]], {
		order: [0, 'asc']
	});
});
