'use strict';
app.service('UtilityService', function(MESSAGE) {

	function allNotEmpty() {
		for (var i = 1; i < arguments.length; i++) {
			if (arguments[i] === null) {
				var response = {success: false, message: MESSAGE.VALIDATION_ERROR};
				arguments[0](response);
				return false;
			}
		}
		return true;
	}

	function setLike(entities, id) {
		for (var i = 0; i < entities.length; i++) {
			if (entities[i].id === id) {
				if (entities[i].liked) {
					--entities[i].rating;
				} else {
					++entities[i].rating;
				}
				entities[i].liked = !entities[i].liked;
				return;
			}
		}
	}

	return {
		allNotEmpty: allNotEmpty,
		setLike: setLike
	};

});