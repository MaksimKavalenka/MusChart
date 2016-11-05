'use strict';
app.filter('trusted', function($sce) {

	return function(url) {
		return $sce.trustAsResourceUrl("https://www.youtube.com/embed/" + url);
	};

});