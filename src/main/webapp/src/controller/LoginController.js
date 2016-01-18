app.controller("LoginController", function($scope, $rootScope, $location, ServerService, LocalStorageService) {

	$scope.user = {};
	var usernameField = $("#username");

	var successfulLogin = function(data, status, headers, config) {
		$rootScope.username = $scope.user.username;
		$rootScope.loggedIn = true;
		ServerService.initWebSocketConnection();
		usernameField.popover("hide");
		$location.path("/chat");
	}

	$scope.signin = function() {
		var name = $scope.user.username;
		if (name == null || name.trim().length < 3 || name.trim().length > 10) {
			showPopOverWithContent("Username must be between 3 and 10 characters");
			return;
		}

		ServerService.login($scope.user.username, "dummy").then(successfulLogin,
				function(data, status, headers, config) {
					showPopOverWithContent("Username already taken");
				});
	};

	function showPopOverWithContent(content) {
		// initialize
		if (usernameField.data("bs.popover") == null) {
			usernameField.popover({
				"container" : "body",
				"title" : "Ooopss...",
			});
		}
		// add content
		usernameField.data("bs.popover").options.content = content;
		usernameField.popover('show');
	}

	// loginTryFromStorage();
});
