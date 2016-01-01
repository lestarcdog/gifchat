app.controller("ChatUserController",function($scope,$rootScope,ServerService) {
	$scope.showAll = false;
	$scope.currentUsers = [];
	var allUsers = null;
	$scope.showAllUsers = function() {
		
	}
	
	var refresh = function() {
		ServerService.currentUsers().success(function(data, status, headers, config) {
			console.log(data);
			allUsers = removeUser($rootScope.username,data);
			$scope.currentUsers = allUsers;
		})
	}
	
	refresh();
	
	var removeUser = function(user,arr) {
		for(i=0; i< arr.length; i++) {
			if(arr[i] == user) {
				arr.slice(i, 1);
				return;
			}
		}
	}
});
