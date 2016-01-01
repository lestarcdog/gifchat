app.controller("ChatController", function($scope, $rootScope, $location, ServerService) {
	var chatBox = $("#chatBox");
	var chatBoxMargin = 100;
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
		if(!$rootScope.loggedIn) {
			$location.path("/");
		}
		
		sizeChatbox();
		ServerService.all().success(function(data, status, headers, config) {
			$scope.messages = data;
			$scope.message = null;
			$scope.typeing();
		});

	}

	function sizeChatbox() {
		chatBox.height(window.innerHeight - chatBoxMargin);
	}

	$(window).resize(function(event) {
		sizeChatbox();
	})

	refresh();
});