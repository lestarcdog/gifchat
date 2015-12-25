app.factory("ServerService", function($http, AppConstants) {
	function get(keyword) {
		return $http.get("http://api.giphy.com/v1/gifs/search?q=" + keyword + AppConstants.api);
	}

	return {
		get : get
	};
});