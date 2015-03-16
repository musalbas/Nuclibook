function prepareDateSelector() {

	// util functions
	var getGeneric = function (seq, type) {
		return $('.date-selector-' + type).filterByData('sequence', seq);
	};
	var getYearSelect = function (seq) {
		return getGeneric(seq, 'year');
	};
	var getMonthSelect = function (seq) {
		return getGeneric(seq, 'month');
	};
	var getDateSelect = function (seq) {
		return getGeneric(seq, 'date');
	};
	var getOutput = function (seq) {
		return getGeneric(seq, 'output');
	};
	var getSequence = function (e) {
		return $(e).attr('data-sequence');
	};

	// create fields
	var dateSelectors = $('div.date-selector');
	var ds, sequence;
	for (var d = 0; d < dateSelectors.length; ++d) {
		ds = dateSelectors.eq(d);
		sequence = ds.data('sequence');
		ds.html('<div class="col-sm-4" style="padding-left: 0;"><select class="form-control date-selector-year" data-sequence="' + sequence + '"></select></div><div class="col-sm-5"><select class="form-control date-selector-month" disabled data-sequence="' + sequence + '"></select></div><div class="col-sm-3" style="padding-right: 0;"><select class="form-control date-selector-date" disabled data-sequence="' + sequence + '"></select></div>');
	}

	// get fields and clear
	var yearFields = $('.date-selector-year');
	var monthFields = $('.date-selector-month');
	var dateFields = $('.date-selector-date');
	yearFields.empty().unbind('change').append('<option value="0" disabled selected>Year</option>');
	monthFields.empty().unbind('change').append('<option value="0" disabled selected>Month</option>');
	dateFields.empty().unbind('change').append('<option value="0" disabled selected>Date</option>');

	// add the last 100 years
	var curYear = (new Date).getFullYear();
	var startYear = curYear - 100;
	for (var i = curYear; i > startYear; --i) {
		yearFields.append('<option value="' + i + '">' + i + '</option>');
	}

	// add months
	monthFields.append('<option value="1">January</option>')
		.append('<option value="2">February</option>')
		.append('<option value="3">March</option>')
		.append('<option value="4">April</option>')
		.append('<option value="5">May</option>')
		.append('<option value="6">June</option>')
		.append('<option value="7">July</option>')
		.append('<option value="8">August</option>')
		.append('<option value="9">September</option>')
		.append('<option value="10">October</option>')
		.append('<option value="11">November</option>')
		.append('<option value="12">December</option>');

	// add days
	for (i = 1; i <= 31; ++i) {
		dateFields.append('<option value="' + i + '" class="d' + i + '">' + i + '</option>');
	}

	// year change listener
	yearFields.change(function () {
		// get sequence
		var seq = getSequence(this);

		// reset
		getMonthSelect(seq).prop('selectedIndex', 0).attr('disabled', 'disabled');
		getDateSelect(seq).prop('selectedIndex', 0).attr('disabled', 'disabled');
		getOutput(seq).val('');

		// enable month?
		if ($(this).val() != 0) {
			getMonthSelect(seq).removeAttr('disabled');
		}
	});

	// month change listener
	monthFields.change(function () {
		// get sequence
		var seq = getSequence(this);

		// reset
		getDateSelect(seq).prop('selectedIndex', 0).attr('disabled', 'disabled');
		getOutput(seq).val('');

		// enable date?
		if ($(this).val() != 0) {
			getDateSelect(seq).removeAttr('disabled');

			// get year and month
			var year = getYearSelect(seq).val();
			var month = $(this).val();

			// disable some dates
			var dateSelector = getDateSelect(seq);
			dateSelector.find('option').removeAttr('disabled');
			dateSelector.find('option').eq(0).attr('disabled', 'disabled');
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				// 30 days for Apr, Jun, Sep and Nov
				dateSelector.find('option.d31').attr('disabled', 'disabled');
			} else if (month == 2) {
				// Feb
				dateSelector.find('option.d30').attr('disabled', 'disabled');
				dateSelector.find('option.d31').attr('disabled', 'disabled');
				if (!((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)) {
					dateSelector.find('option.d29').attr('disabled', 'disabled');
				}
			}
		}
	});

	// date change listener
	dateFields.change(function () {
		// get sequence
		var seq = getSequence(this);

		// reset
		getOutput(seq).val('');

		// get info
		var year = getYearSelect(seq).val();
		var month = getMonthSelect(seq).val();
		var date = $(this).val();

		// date selected?
		if (date == 0) return;

		// update output
		var output = year + '-' + (month < 10 ? '0' : '') + month + '-' + (date < 10 ? '0' : '') + date;
		getOutput(seq).val(output);
	});

	// attempt pre-fill
	$('.date-selector-output').each(function (i, e) {
		// match?
		if ($(e).val().match(/[0-9]{4}\-[0-9]{1,2}\-[0-9]{1,2}/)) {
			// split
			var parts = $(e).val().split('-', 3);

			// get sequence
			var seq = getSequence(e);

			// set values
			getYearSelect(seq).val(parseInt(parts[0])).trigger('change');
			getMonthSelect(seq).val(parseInt(parts[1])).trigger('change');
			getDateSelect(seq).val(parseInt(parts[2])).trigger('change');
		}
	});
}