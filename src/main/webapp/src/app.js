var app = angular.module("GifApp", [ "ngRoute", "angularMoment","bootstrapLightbox" ]);

app.config(function($routeProvider, $httpProvider) {
	$routeProvider.when("/login", {
		templateUrl : "views/login.html",
		controller : "LoginController"
	}).when("/chat", {
		templateUrl : "views/chatbox.html",
		controller : "ChatController"
	}).otherwise("/login");

	$httpProvider.interceptors.push(function() {
		return {
			'responseError' : function(rejection) {
				console.log("rejection :" + rejection.data +" status: "+ rejection.status);
			}
		}
	});
});

app.run(function($rootScope) {
	$rootScope.loggedIn = false;
	$rootScope.username = null;
});

app.constant("SessionStorageConst", {
	"usernameKey" : "username"
});

app.constant("BaseUrlConst", '${baseUrlConst}');
