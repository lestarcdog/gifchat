app.directive("chatBox", function() {
	return {
		templateUrl: "src/template/chatbox-template.html",
		controller: "ChatBoxController",
		link: function(scope,element,attrs) {
			var chatBox = angular.element("#chatBox");
			
			//resize chatbox on window resize
			var sizeChatbox = function() {
				chatBox.height(window.innerHeight - CHATBOX_CONST.chatBoxMargin);
				chatBoxLazyLoadHeight = Math.floor(chatBox.height() * 0.2);
			};

			$(window).resize(function(event) {
				sizeChatbox();
			});
			
			sizeChatbox();
		}
	};
});