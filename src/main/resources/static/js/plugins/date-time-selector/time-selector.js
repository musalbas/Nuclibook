function prepareTimeSelector() {

	// util functions
	var getGeneric = function (seq, type) {
		return $('.time-selector-' + type).filterByData('sequence', seq);
	};
	var getHourSelect = function (seq) {
		return getGeneric(seq, 'hour');
	};
	var getMinSelect = function (seq) {
		return getGeneric(seq, 'min');
	};
	var getOutput = function (seq) {
		return getGeneric(seq, 'output');
	};
	var getSequence = function (e) {
		return $(e).attr('data-sequence');
	};

	// create fields
	var timeSelectors = $('div.time-selector');
	var ts, sequence;
	for (var t = 0; t < timeSelectors.length; ++t) {
		ts = timeSelectors.eq(t);
		sequence = ts.data('sequence');
		ts.html('<div class="col-sm-12" style="padding: 0;"><select class="form-control time-selector-hour" data-sequence="' + sequence + '" style="display: inline; width: auto;"></select>&nbsp;&nbsp;<strong>:</strong>&nbsp;&nbsp;<select class="form-control time-selector-min" data-sequence="' + sequence + '" style="display: inline; width: auto;"></select></div>');
	}

	// get fields and clear
	var hourFields = $('.time-selector-hour');
	var minFields = $('.time-selector-min');
	hourFields.empty().unbind('change').append('<option value="x" disabled selected>HH</option>');
	minFields.empty().unbind('change').append('<option value="x" disabled selected>MM</option>');

	// add hours
	for (var i = 0; i < 24; ++i) {
		hourFields.append('<option value="' + i + '">' + (i < 10 ? '0' : '') + i + '</option>');
	}

	// add hours
	for (i = 0; i < 60; ++i) {
		minFields.append('<option value="' + i + '">' + (i < 10 ? '0' : '') + i + '</option>');
	}

	// hour change listener
	hourFields.change(function () {
		// get sequence
		var seq = getSequence(this);

		// get values
		var hour = $(this).val();
		var min = getMinSelect(seq).val();

		// update output
		if (hour == 'x' || min == 'x') {
			getOutput(seq).val('');
		} else {
			getOutput(seq).val((hour < 10 ? '0' : '') + hour + ':' + (min < 10 ? '0' : '') + min);
		}
	});

	// min change listener
	minFields.change(function () {
		// get sequence
		var seq = getSequence(this);

		// get values
		var hour = getHourSelect().val();
		var min = $(this).val();

		// update output
		if (hour == 'x' || min == 'x') {
			getOutput(seq).val('');
		} else {
			getOutput(seq).val((hour < 10 ? '0' : '') + hour + ':' + (min < 10 ? '0' : '') + min);
		}
	});

	// attempt pre-fill
	$('.time-selector-output').each(function (i, e) {
		// match?
		if ($(e).val().match(/[0-9]{2}:[0-9]{2}/)) {
			// split
			var parts = $(e).val().split(':', 2);

			// get sequence
			var seq = getSequence(e);

			// set values
			getHourSelect(seq).val(parseInt(parts[0])).trigger('change');
			getMinSelect(seq).val(parseInt(parts[1])).trigger('change');
		}
	});
}