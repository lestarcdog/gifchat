app.controller("ChatController", function($scope, $rootScope, $location, ServerService, Lightbox) {
    var userTxtField = angular.element("#user-txt-field");
    $scope.sending = false;
    $scope.message = null;
    $scope.textMaxLength = 100;

    $scope.remainingChars = $scope.textMaxLength;

    var sendMessageEnd = function() {
        $scope.sending = false;
        $scope.message = null;
        $scope.remainingChars = $scope.textMaxLength;
        // need timeout because disabled field to run the digest
        setTimeout(function() {
            userTxtField.focus();
        }, 500);

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

    function refresh() {
        if (!$rootScope.loggedIn) {
            $location.path("/");
        }
    }

    refresh();
});