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
			var input, select;
			for (var key in data) {
				// basic strings
				if (typeof data[key] == 'string') {
					input = form.find('input[name=' + key + ']');
					if (input.length) {
						input.val(data[key]);
					} else {
						select = form.find('select[name=' + key + ']');
						select.val(data[key]).attr('selected', 'selected');
					}
				}

				// id lists
				if (typeof data[key] == 'object') {
					for (var i = 0; i < data[key].length; ++i) {
						$('.crud-list-prefill-' + key + '[value=' + data[key][i] + ']').attr('checked', 'checked')
					}
				}
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

		// show loading modal
		enableLoading();

		// ajax!
		$.post(
			'/entity-update',
			form.serialize()
		).done(function (result) {
				if (result == 'okay') {
					// hide modal
					editModal.modal('hide');

					// hide loading and reload
					disableLoading(function () {
						// TODO: better solution to this
						location.reload();
					});
				} else if (result == 'failed_validation') {
					disableLoading(function () {
						toastr.error('The data you entered was invalid; please check again');
					});
				} else if (result == 'no_permission') {
					disableLoading(function () {
						toastr.error('You do not have permission to edit or create this item');
					});
				} else {
					disableLoading(function () {
						toastr.error('Something went wrong; please try again');
					});
				}
			}
		).fail(function () {
				disableLoading(function () {
					toastr.error('Something went wrong; please try again');
				});
			}
		);
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
		// show loading modal
		enableLoading();

		// ajax!
		$.post(
			'/entity-delete',
			form.serialize()
		).done(function (result) {
				if (result == 'okay') {
					// hide modal
					deleteModal.modal('hide');

					// hide loading and reload
					disableLoading(function () {
						// TODO: better solution to this
						location.reload();
					});
				} else if (result == 'no_permission') {
					disableLoading(function () {
						toastr.error('You do not have permission to delete this item');
					});
				} else {
					disableLoading(function () {
						toastr.error('Something went wrong; please try again');
					});
				}
			}
		).fail(function () {
				disableLoading(function () {
					toastr.error('Something went wrong; please try again');
				});
			}
		);
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