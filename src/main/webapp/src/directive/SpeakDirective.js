app.directive("speak", function(ServerService) {
	return {
		templateUrl: "src/template/speak-template.html",
		scope : {
			"msg" : "="
		},
		controller: function($scope,ServerService) {
			var progress = false;
			$scope.speak = function(msg) {
				//do not progress twice
				if(progress) {
					return;
				}
				console.log("speak "+msg.id);
				console.log(msg);
				progress = true;
				ServerService.makeSoundOfMessage(msg.id).then(function(response) {
					console.log(response);
					progress = false;
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