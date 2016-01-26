app.directive("speak", function(ServerService,$rootScope) {
	return {
		templateUrl: "src/template/speak-template.html",
		scope : {
			"msg" : "="
		},
		controller: function($scope,ServerService) {
			$scope.progress = false;
			$scope.speak = function(msg) {
				//do not progress twice
				if($scope.progress) {
					return;
				}
				console.log("speak "+msg.id);
				$scope.progress = true;
				ServerService.makeSoundOfMessage(msg.id).then(function(response) {
					console.log(response);
					$scope.progress = false;
					msg.hasSound = true;
				});
			};
			
			$scope.getSound = function(msg) {
				if(msg.hasSound) {
					return ServerService.getSoundOfMessage(msg.id);
				} else {
					return "#";
				}
			};
		}
	};
});