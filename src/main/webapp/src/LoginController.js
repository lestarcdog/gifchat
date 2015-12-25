app.controller("LoginController", function($scope, $rootScope) {
	$scope.signin = function() {
		$rootScope.loggedIn = true;
		$rootScope.username = $scope.username;
	};
});