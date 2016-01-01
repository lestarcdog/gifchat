app.directive("chatMessageScroll", function($location) {
	var chatBox = $("#chatBox");
	var anchor = $("#lastAnchor");
	
	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$last) {
				setTimeout(function() {
					//console.log(anchor.position().top);
					chatBox.scrollTop(anchor.position().top);
				}, 1000);

			}
		}
	};
});