app.controller("MainController", function($scope, ServerService) {
	var chatBox = angular.element("#chatBox");
	$scope.sending = false;
	$scope.messages = [];
	$scope.textMaxLength = 100;
	$scope.remainingChars = $scope.textMaxLength;
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

	$scope.typeing = function() {
		$scope.remainingChars = $scope.textMaxLength - $scope.message.length;
	};

	function refresh() {
		ServerService.all().success(function(data, status, headers, config) {
			console.log(data);
			$scope.messages = data;
		});
	}

	refresh();
});