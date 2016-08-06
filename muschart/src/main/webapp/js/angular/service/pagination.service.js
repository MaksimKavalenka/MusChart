'use strict';
app.service('PaginationService', function() {
	return {

		getPages: function(page, path, callback) {
			var pages = [];
			var from = page - 2;
			if (page <= 3) {
				from = 1;
			}
			for (var i = from; i < from + 5; i++) {
				pages.push({
					number: i,
					link: path + '({page:' + i + '})'
				});
			}
			callback(pages);
		}

	};
});