var editModal, deleteModal, loadingModal;

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
	deleteModal = $('.delete-modal');
	loadingModal = $('.loading-modal');

	// link up clickable items
	$('.create-button').click(function (e) {
		openEditModal(0);
	});
	$('.edit-button').click(function (e) {
		openEditModal($(this).attr('data-id'));
	});
	$('.delete-button').click(function (e) {
		openDeleteModal($(this).attr('data-id'));
	});

});

function openEditModal(objectId) {
	// find form
	var form = editModal.find('.edit-form');
	form.find('input[name=entity-id]').val(objectId);

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

		// hide the modal
		editModal.modal('hide');

		// show loading modal
		enableLoading();

		// ajax!
		$.post(
			'/entity-update',
			form.serialize(),
			function (result) {
				disableLoading(function () {
					// TODO: better solution to this
					location.reload();
				});
			});
	});

	// display modal
	editModal.removeClass('hide').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function openDeleteModal(objectId) {
	// find form
	var form = deleteModal.find('.delete-form');
	form.find('input[name=entity-id]').val(objectId);

	// cancel button
	deleteModal.find('.btn-cancel').unbind('click').click(function (e) {
		deleteModal.modal('hide');
	});

	// save button
	deleteModal.find('.btn-okay').unbind('click').click(function (e) {
		// hide the modal
		deleteModal.modal('hide');

		// show loading modal
		enableLoading();

		// ajax!
		$.post(
			'/entity-delete',
			form.serialize(),
			function (result) {
				disableLoading(function () {
					// TODO: better solution to this
					location.reload();
				});
			});
	});

	// display modal
	deleteModal.removeClass('hide').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function enableLoading() {
	loadingModal.removeClass('hide').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function disableLoading(onComplete) {
	setTimeout(function () {
		loadingModal.modal('hide');
		if (typeof(onComplete) == 'function') onComplete();
	}, 1000);
}

// toastr options
toastr.options = {
	"closeButton": true,
	"debug": false,
	"positionClass": "toast-top-right",
	"onclick": null,
	"showDuration": "1000",
	"hideDuration": "1000",
	"timeOut": "4000",
	"extendedTimeOut": "1000",
	"showEasing": "swing",
	"hideEasing": "linear",
	"showMethod": "fadeIn",
	"hideMethod": "fadeOut"
};