var editModal, loadingModal;

$.fn.serializeObject = function () {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function () {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$(document).ready(function (e) {

	// get modals
	editModal = $('.edit-modal');
	loadingModal = $('.loading-modal');


	// link up clickable items
	$('.create-button').click(function (e) {
		openEditModal(0);
	});
	$('.edit-button').click(function (e) {
		openEditModal($(this).attr('data-id'));;
	});

});

function openEditModal(objectId) {
	// find form
	var form = editModal.find('.edit-form');

	// switch between edit/create components
	if (objectId == 0) {
		editModal.find('.edit-mode').hide();
		editModal.find('.create-mode').show();
	} else {
		editModal.find('.edit-mode').show();
		editModal.find('.create-mode').hide();
	}

	// reset form
	form[0].reset();

	// pre-fill form
	if (objectId > 0) {
		var data = objectMap[objectId];
		if (typeof(data) != 'undefined') {
			for (var key in data) {
				form.find('input[name=' + key + ']').val(data[key]);
			}
		}
	}

	// cancel button
	editModal.find('.btn-cancel').unbind('click').click(function (e) {
		editModal.modal('hide');
	});

	// save button
	editModal.find('.btn-save').unbind('click').click(function (e) {
		// run validation functions
		if (objectId == 0) {
			if (typeof(validateCreateForm) == 'function' && !validateCreateForm(form.serializeObject())) return;
		} else {
			if (typeof(validateEditForm) == 'function' && !validateEditForm(form.serializeObject())) return;
		}

		// serialise the form
		var serialised = form.serialize();

		// hide the modal
		editModal.modal('hide');

		// show loading modal
		showLoadingModal();

		// TODO: ajax

		setTimeout(function () {
			hideLoadingModal();
		}, 2000);
	});

	// display modal
	editModal.removeClass('hide').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function showLoadingModal() {
	loadingModal.removeClass('hide').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function hideLoadingModal() {
	loadingModal.modal('hide');
}