app.controller("MainController", function($scope, ServerService) {
	var chatBox = angular.element("#chatBox");
	$scope.sending = false;
	$scope.dummy = {
		"username" : "Niki",
		"userText" : "asdf",
		"sentTime" : 123456

	}
	$scope.search = function() {
		$scope.sending = true;

	};

});