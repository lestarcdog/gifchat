app.controller("LoginController", function($scope, $rootScope, ServerService) {
	$scope.user = {};
	$scope.signin = function() {
		ServerService.login($scope.user.username, "dummy").success(function(data, status, headers, config) {
			$rootScope.username = $scope.user.username;
			$rootScope.loggedIn = true;
		});
	};
});