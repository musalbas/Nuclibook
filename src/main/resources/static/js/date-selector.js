function prepareDateSelector() {
	// get fields
	var yearField = $('#date-selector-year');
	var monthField = $('#date-selector-month');
	var dayField = $('#date-selector-day');
	var outputField = $('#date-selector-output');

	// clear!
	yearField.empty().unbind('change').append('<option value="" disabled selected>Year</option>');
	monthField.empty().unbind('change').append('<option value="" disabled selected>Month</option>');
	dayField.empty().unbind('change').append('<option value="" disabled selected>Day</option>');

	// add the last 100 years
	var curYear = (new Date).getFullYear();
	var startYear = curYear - 100;
	for (var i = curYear; i > startYear; --i) {
		yearField.append('<option value="' + i + '">' + i + '</option>');
	}

	// add months
	monthField.append('<option value="01">January</option>')
		.append('<option value="02">February</option>')
		.append('<option value="03">March</option>')
		.append('<option value="04">April</option>')
		.append('<option value="05">May</option>')
		.append('<option value="06">June</option>')
		.append('<option value="07">July</option>')
		.append('<option value="08">August</option>')
		.append('<option value="09">September</option>')
		.append('<option value="10">October</option>')
		.append('<option value="11">November</option>')
		.append('<option value="12">December</option>');

	// year click handler
	yearField.change(function (e) {
		var selectedYear = $(this).find('option:selected').text();
		if ($.isNumeric(selectedYear)) {
			monthField.removeAttr('disabled');
			monthField.find('option').attr('selected', '');
			monthField.find('option').eq(0).attr('selected', 'selected');
			dayField.attr('disabled', '')
				.empty()
				.append('<option disabled selected>Day</option>');
		}
	});

	// month click
	monthField.change(function (e) {
		// Starting to open days checking what month and year is
		var selectedYear = parseInt(yearField.find('option:selected').text(), 10);
		var selectedMonth = monthField.find('option:selected').text();
		var monthsArr = ['January', 'March', 'May', 'July', 'August', 'October', 'December'];
		dayField.empty()
			.append('<option disabled selected>Day</option>');

		if ($.inArray(selectedMonth, monthsArr) > -1) {
			for (i = 1; i <= 31; ++i) {
				dayField.append('<option value="' + i + '">' + i + '</option>');
			}
		} else {
			if (selectedMonth == "February") {
				if (((selectedYear % 4 == 0) && (selectedYear % 100 != 0)) || (selectedYear % 400 == 0)) {
					for (i = 1; i <= 29; ++i) {
						dayField.append('<option value="' + i + '">' + i + '</option>');
					}
				} else {
					for (i = 1; i <= 28; ++i) {
						dayField.append('<option value="' + i + '">' + i + '</option>');
					}
				}
			} else {
				for (i = 1; i <= 30; ++i) {
					dayField.append('<option value="' + i + '">' + i + '</option>');
				}
			}
		}

		if (selectedMonth != "Month") {
			dayField.removeAttr('disabled');
		}
	});

	// day click
	dayField.change(function (e) {
		var selectedYear = yearField.find('option:selected').val();
		var selectedMonth = monthField.find('option:selected').val();
		var selectedDay = dayField.find('option:selected').val();
		outputField.val(selectedYear + "-" + selectedMonth + "-" + (selectedDay < 10 ? '0' : '') + selectedDay);
	});
}