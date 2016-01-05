app.directive("chatMessageScroll", function($location) {

	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$last) {
				setTimeout(function() {
					var chatBox = $("#chatBox");
					//console.log("ScrollHeight: "+chatBox.prop("scrollHeight"));
					chatBox.scrollTop(chatBox.prop("scrollHeight"));
				}, 1500);

			}
		}
	};
});