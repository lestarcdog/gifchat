app.controller("ChatBoxController", [ "$scope", "ServerService", "$timeout", "Lightbox",
		function($scope, ServerService, $timeout, Lightbox) {
			var MAX_MESSAGE = 20;

			$scope.isLoading = false;
			$scope.messages = [];
			$scope.loadedImagesSize = 1000;
			var chatBox = angular.element("#chatBox");
			var chatBoxMargin = 100;
			var chatBoxLazyLoadHeight = 25;
			$scope.chatBoxLazyLoading = false;

			var loadMore = function(evt) {
				//user is scrolling but still loading
				if (chatBox.scrollTop() > chatBoxLazyLoadHeight || $scope.chatBoxLazyLoading) {
					return;
				}

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

			chatBox.scroll(loadMore);

			$scope.openOriginal = function(msg) {
				var img = new LightBoxImage(msg);
				var imgs = [ img ];
				Lightbox.openModal(imgs, 0);
			};

			var sizeChatbox = function() {
				chatBox.height(window.innerHeight - chatBoxMargin);
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
					while ($scope.messages.length > MAX_MESSAGE) {
						$scope.messages.shift();
					}
					$scope.messages.push(gifMessage);
				});

			};

			ServerService.addListeners(onNewMessage);

		} ]);