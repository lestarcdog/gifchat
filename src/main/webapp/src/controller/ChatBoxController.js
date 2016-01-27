app.controller("ChatBoxController", [ "$scope", "ServerService", "$timeout", "Lightbox",
		function($scope, ServerService, $timeout, Lightbox) {

			$scope.isLoading = false;
			$scope.messages = [];
			$scope.loadedImagesSize = 1000;
			var chatBox = angular.element("#chatBox");
			var scrollMode = SCROLL_BAR_MODE.NEWEST;
			// var showNotification = false;
			$scope.chatBoxLazyLoading = false;
			
			$scope.getScrollMode = function() {
				return scrollMode;
			};

			var loadMoreMessages = function() {
				if ($scope.messages.length === 0) {
					return;
				} else {
					$scope.$apply(function() {
						$scope.chatBoxLazyLoading = true;
					});
					$timeout(function() {
						var firstMsg = $scope.messages[0];
						ServerService.earlierMessages(firstMsg).then(function(value) {
							if (value != null && value.length !== 0) {
								$scope.messages = value.concat($scope.messages);
								$scope.loadedImagesSize = value.length - 1;
							}
							$scope.chatBoxLazyLoading = false;
						});
					}, 1500);
				}

			};

			var selectScrollMode = function(evt) {
				console.log()
				if (chatBox.scrollTop() < CHATBOX_CONST.chatBoxLazyLoadHeight) {
					scrollMode = SCROLL_BAR_MODE.LOADING;
					if(!$scope.chatBoxLazyLoading) {
						loadMoreMessages();
					}
				} else if((chatBox.prop("scrollHeight") - chatBox.innerHeight() - chatBox.scrollTop()) < CHATBOX_CONST.chatBoxLazyLoadHeight) {
					scrollMode = SCROLL_BAR_MODE.NEWEST;
				} else {
					scrollMode = SCROLL_BAR_MODE.BROWSING;
				}
				console.log(scrollMode);
			};

			chatBox.scroll(selectScrollMode);

			$scope.openOriginal = function(msg) {
				var img = new LightBoxImage(msg);
				var imgs = [ img ];
				Lightbox.openModal(imgs, 0);
			};

			var sizeChatbox = function() {
				chatBox.height(window.innerHeight - CHATBOX_CONST.chatBoxMargin);
				chatBoxLazyLoadHeight = Math.floor(chatBox.height() * 0.2);
			};

			$(window).resize(function(event) {
				sizeChatbox();
			});

			var refresh = function() {
				ServerService.lastMessages().then(function(data) {
					$scope.messages = data;
				});
				sizeChatbox();
			};

			refresh();

			// on new message listener
			var onNewMessage = function(gifMessage) {
				console.log("== DEBUG INCOMING MSG ==");
				console.log(gifMessage);
				$scope.$apply(function() {
					while ($scope.messages.length > CHATBOX_CONST.MAX_MESSAGE) {
						$scope.messages.shift();
					}
					$scope.messages.push(gifMessage);
				});
			};

			ServerService.addListeners(onNewMessage);

			// $scope.$on("visibilityChanged", function(evt, args) {
			// showNotification = args;
			// });
		} ]);

/* The scrollbar works in 3 states
	LOADING - the user loads the old messages
	BROWSING - in the middle browsing beetween messages
	NEWEST - at the bottom of the chatbox
*/
var SCROLL_BAR_MODE = Object.freeze({
	LOADING : "LOADING",
	BROWSING : "BROWSING",
	NEWEST : "NEWEST"
});

var CHATBOX_CONST = Object.freeze({
	chatBoxMargin : 100,
	chatBoxLazyLoadHeight : 25,
	MAX_MESSAGE : 20
});