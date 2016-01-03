app.controller("ChatUserController",function($scope,$rootScope,ServerService) {
	$scope.showAll = false;
	$scope.currentUsers = [];
	
	$scope.refresh = function() {
		ServerService.currentTopUsers().success(function(data, status, headers, config) {
			$scope.currentUsers = data;
		})
	}
	
	$scope.refresh();
	
});
