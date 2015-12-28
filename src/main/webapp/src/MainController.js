app.controller("MainController", function($scope, ServerService) {
	var chatBox = $("#chatBox");
	$scope.sending = false;
	$scope.messages = [];
	$scope.textMaxLength = 100;
	$scope.message = null;
	$scope.remainingChars = $scope.textMaxLength;

	$scope.sendMessage = function() {
		$scope.sending = true;
		var msg = $scope.message;
		ServerService.newMessage(msg).success(function(data, status, headers, config) {
			refresh();
			$scope.sending = false;
		});
	};

	$scope.typeing = function() {
		if ($scope.message != null) {
			$scope.remainingChars = $scope.textMaxLength - $scope.message.length;
		} else {
			$scope.remainingChars = $scope.textMaxLength;
		}
	};

	function refresh() {
		ServerService.all().success(function(data, status, headers, config) {
			$scope.messages = data;
		});
	}

	refresh();
});