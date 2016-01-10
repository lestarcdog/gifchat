app.controller("ChatBoxController", ["$scope", "ServerService", "Lightbox",
		function($scope, ServerService, Lightbox) {
			$scope.isLoading = false;
			$scope.messages = [];

			$scope.loadMore = function() {
				if ($scope.messages.length === 0) {
					console.log("should not be loading anything");
					return;
				} else {
					var firstMsg = messages[0];

					//TODO call
					$scope.messages.unshift(null);

				}

			}

			var refresh = function() {
				ServerService.all().then(function(data) {
					$scope.messages = data;
				});
				sizeChatbox();
			}

			$scope.openOriginal = function(msg) {
				var img = new LightBoxImage(msg);
				var imgs = [img];
				Lightbox.openModal(imgs, 0);
			};

			refresh();

			// on new message listener
			var onNewMessage = function(gifMessage) {
				$scope.messages.push(gifMessage);
			}

			ServerService.addListeners(onNewMessage);

		}]);