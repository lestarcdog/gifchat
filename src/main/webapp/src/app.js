var app = angular.module("GifApp", []);

app.constant("AppConstants", {
	"api" : "&api_key=dc6zaTOxFJmzC"
});

app.run(function($rootScope) {
	$rootScope.loggedIn = false;

	// delete me
	$rootScope.loggedIn = true;
	$rootScope.username = "Niki";
});
