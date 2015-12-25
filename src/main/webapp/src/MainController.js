app.controller("MainController", function($scope, ServerService) {
	var chatBox = angular.element("#chatBox");
	$scope.sending = false;
	$scope.search = function() {
		$scope.sending = true;

	};
});