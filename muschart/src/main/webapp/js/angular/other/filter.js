'use strict';
app.filter('trusted', ['$sce', function($sce) {
	return function(url) {
		return $sce.trustAsResourceUrl("https://www.youtube.com/embed/" + url);
	};
}]);