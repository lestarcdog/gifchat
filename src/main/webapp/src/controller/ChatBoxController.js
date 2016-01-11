app.controller("ChatBoxController", [ "$scope", "ServerService", "Lightbox", function($scope, ServerService, Lightbox) {
    $scope.isLoading = false;
    $scope.messages = [];
    var chatBox = angular.element("#chatBox");
    var chatBoxMargin = 100;
    var chatBoxLazyLoadHeight = 25;
    var chatBoxLazyLoading = false;

    var loadMore = function(evt) {
        if (chatBox.scrollTop() > chatBoxLazyLoadHeight || chatBoxLazyLoading) {
            return;
        }

        if ($scope.messages.length === 0) {
            console.log("should not be loading anything");
            return;
        } else {
            chatBoxLazyLoading = true;
            var firstMsg = $scope.messages[0];
            ServerService.earlierMessages(firstMsg).then(function(value) {
                console.log(value);
                if (value != null && value.length !== 0) {
                    // prepend the new valeus
                    $scope.messages = value.concat($scope.messages);
                }
                chatBoxLazyLoading = false;
            });
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
    	console.log(gifMessage)
        $scope.messages.push(gifMessage);
    };

    ServerService.addListeners(onNewMessage);

} ]);