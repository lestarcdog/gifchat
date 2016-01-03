app.directive("chatMessageScroll", function($location) {

	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$last) {
				setTimeout(function() {
					var anchor = $("#lastAnchor");
					var chatBox = $("#chatBox");
					console.log(anchor.position().top);
					chatBox.scrollTop(anchor.position().top);
				}, 3000);

			}
		}
	};
});