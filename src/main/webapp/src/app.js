var app = angular.module("GifApp", []);

app.constant("AppConstants", {
	"api" : "&api_key=dc6zaTOxFJmzC"
});

app.run(function($rootScope) {
	$rootScope.loggedIn = false;

	$rootScope.loggedIn = true;
	$rootScope.username = "Jóska";
});
