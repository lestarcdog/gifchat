app.controller("ChatUserController", function($scope, $rootScope, ServerService) {
    $scope.showAll = false;
    $scope.currentUsers = [];

    $scope.refresh = function() {
        ServerService.currentTopUsers().then(function(data) {
            $scope.currentUsers = data;
        });
    };

    $scope.refresh();

});
