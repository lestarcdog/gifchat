var app = angular.module("GifApp", []);

app.run(function($rootScope) {
	$rootScope.loggedIn = false;
	$rootScope.username = null;

});
