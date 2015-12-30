app.directive("chatMessageScroll", function($location) {
	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$last) {

				setTimeout(function() {
					var chatBox = $("#chatBox");
					var anchor = $("#lastAnchor");
					console.log(anchor.position().top);
					chatBox.scrollTop(anchor.position().top);
				}, 2000);

			}
		}
	};
});