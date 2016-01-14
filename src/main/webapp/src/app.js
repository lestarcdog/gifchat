var app = angular.module("GifApp", [ "ngRoute", "angularMoment", "bootstrapLightbox" ]);

app.config(function($routeProvider, LightboxProvider) {
    $routeProvider.when("/login", {
        templateUrl : "views/login.html",
        controller : "LoginController"
    }).when("/chat", {
        templateUrl : "views/chatbox.html",
        controller : "ChatController"
    }).otherwise("/login");

    // lightbox template
    LightboxProvider.templateUrl = "src/template/lightbox-template.html";
});

app.run(function($rootScope) {
    $rootScope.loggedIn = false;
    $rootScope.username = null;
});

app.constant("SessionStorageConst", {
    "usernameKey" : "username"
});

app.constant("BaseUrlConst", '${baseUrlConst}');
