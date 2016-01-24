app.directive("speak", function(ServerService) {
	return {
		templateUrl: "src/template/speak-template.html",
		scope : {
			"msg" : "="
		},
		controller: function($scope,ServerService) {
			$scope.speak = function(msg) {
				console.log("speak "+msg.id);
			};
			$scope.getSound = function(msg) {
				console.log("getting sound for "+msg.id);
				return "#";
			};
		}
	};
});