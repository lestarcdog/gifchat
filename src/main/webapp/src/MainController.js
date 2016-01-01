app.controller("MainController", function($scope, ServerService) {
	$scope.loggedIn = false;
	$scope.username = null;
	$scope.user = {};
	var usernameField = $("#username");

	$scope.signin = function() {
		var name = $scope.user.username;
		if (name == null || name.trim().length < 3 || name.trim().length > 10) {
			showPopOverWithContent("Username must be between 3 and 10 characters");
			return;
		}

		ServerService.login($scope.user.username, "dummy").success(function(data, status, headers, config) {
			$scope.username = $scope.user.username;
			$scope.loggedIn = true;
			usernameField.popover("hide");
		}).error(function(data, status, headers, config) {
			showPopOverWithContent("Username already taken");
		});
	};

	function showPopOverWithContent(content) {
		//initialize
		if (usernameField.data("bs.popover") == null) {
			usernameField.popover({
				"container" : "body",
				"title" : "Ooopss...",
			});
		}
		//add content
		usernameField.data("bs.popover").options.content = content;
		usernameField.popover('show');
	}
});
