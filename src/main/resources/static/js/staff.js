var validateCreateForm = function(formObject) {
	// check name
	if (formObject.username.length < 1) {
		alert("You did not enter a valid username");
		return false;
	}

	// TODO: check password length and match

	return true;
};