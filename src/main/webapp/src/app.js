var app = angular.module("GifApp", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider
	.when("/login", {
		templateUrl: "views/login.html",
		controller: "LoginController"
	})
	.when("/chat", {
		templateUrl: "views/chatbox.html",
		controller: "ChatController"
	})	
	.otherwise("/login");
});

app.run(function($rootScope) {
	$rootScope.loggedIn = false;
	$rootScope.username = null;
});

app.constant("SessionStorageConst", {
	"usernameKey" : "username"
});

app.constant("BaseUrlConst", '/${baseUrl}');
