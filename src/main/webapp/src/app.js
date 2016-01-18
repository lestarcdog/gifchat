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

app.run(function($rootScope,LocalStorageService,SessionStorageConst) {
    $rootScope.loggedIn = false;
    $rootScope.username = null;
    
    //request notification permission
    Notification.requestPermission(function(result) {
    	  if (result === 'denied') {
    	    console.log('Permission wasn\'t granted. Allow a retry.');
    	    return;
    	  } else if (result === 'default') {
    	    console.log('The permission request was dismissed.');
    	    return;
    	  }
    	  LocalStorageService.add(SessionStorageConst.showNotification,result);
    });
    
    //broadcast event if the tab is hidden
    visibilityChangeModule($rootScope,"visibilityChanged");
    
});

app.constant("SessionStorageConst", {
    "usernameKey" : "username",
    "showNotification" : "showNotification"
});

app.constant("BaseUrlConst", '${baseUrlConst}');
