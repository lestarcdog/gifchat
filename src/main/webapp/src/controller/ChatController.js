app.controller("ChatController", function($scope, $rootScope, $location, ServerService, Lightbox) {
	var chatBox = angular.element("#chatBox");
	var chatBoxMargin = 100;
	var userTxtField = angular.element("#user-txt-field");
	$scope.sending = false;
	$scope.messages = [];
	$scope.textMaxLength = 100;
	$scope.message = null;
	$scope.remainingChars = $scope.textMaxLength;

	var sendMessageEnd = function() {
		$scope.sending = false;
		$scope.message = null;
		$scope.remainingChars = $scope.textMaxLength;
		// need timeout because disabled field to run the digest
		setTimeout(function() {
			userTxtField.focus();
		}, 1000);

	};

	$scope.sendMessage = function() {
		$scope.sending = true;
		var msg = $scope.message;
		ServerService.newMessage(msg).then(function(data) {
			sendMessageEnd();
		}, function(data) {
			sendMessageEnd();
		});
	};

	$scope.typeing = function() {
		if ($scope.message != null) {
			$scope.remainingChars = $scope.textMaxLength - $scope.message.length;
		} else {
			$scope.remainingChars = $scope.textMaxLength;
		}
	};

	$scope.openOriginal = function(msg) {
		var img = new LightBoxImage(msg);
		console.log(img);
		var imgs = [ img ];
		Lightbox.openModal(imgs, 0);
	};

	function refresh() {
		if (!$rootScope.loggedIn) {
			$location.path("/");
		}

		sizeChatbox();
		ServerService.all().then(function(data) {
			$scope.messages = data;
			$scope.message = null;
			$scope.typeing();
		});

	}

	refresh();

	function sizeChatbox() {
		chatBox.height(window.innerHeight - chatBoxMargin);
	}

	var scrollInterval = function() {
		var sh = chatBox.prop("scrollHeight");
		console.log(sh);
		if (sh - chatBox.scrollTop > 100)
			chatBox.scrollTop(sh);
	}

	// chatbox on resize
	$(window).resize(function(event) {
		sizeChatbox();
	})

	// on new message listener
	var onNewMessage = function(gifMessage) {
		$scope.messages.push(gifMessage);
	}

	ServerService.addListeners(onNewMessage);
});