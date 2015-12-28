app.controller("MainController", function($scope, ServerService) {
	var chatBox = angular.element("#chatBox");
	$scope.sending = false;
	$scope.messages = [];
	$scope.search = function() {
		$scope.sending = true;

	};

	$scope.sendMessage = function() {
		$scope.sending = true;
		var msg = $scope.message;
		ServerService.newMessage(msg).success(function(data, status, headers, config) {
			refresh();
			$scope.sending = false;
		});
	};

	function refresh() {
		ServerService.all().success(function(data, status, headers, config) {
			console.log(data);
			$scope.messages = data;
		});
	}

	refresh();
});