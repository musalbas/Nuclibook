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

	// update output
	var updateOutput = function (seq) {
		// get values
		var hour = getHourSelect(seq).val();
		var min = getMinSelect(seq).val();

		// update output
		var out = getOutput(seq);
		if (hour == 'x' || min == 'x') {
			if (out.attr('data-default') != null) {
				out.val(out.attr('data-default'));
			} else {
				out.val('');
			}
		} else {
			out.val((hour < 10 ? '0' : '') + hour + ':' + (min < 10 ? '0' : '') + min);
		}
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
	hourFields.empty().unbind('change').append('<option value="x" selected>HH</option>');
	minFields.empty().unbind('change').append('<option value="x" selected>MM</option>');

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
		updateOutput(getSequence(this));
	});

	// min change listener
	minFields.change(function () {
		updateOutput(getSequence(this));
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
		} else {
			if ($(e).attr('data-default') != null) {
				$(e).val($(e).attr('data-default'));
			}
		}
	});
}