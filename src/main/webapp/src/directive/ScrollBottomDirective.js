app.directive("scrollBottom", function() {

	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$last) {
				var chatBox = $("#chatBox");
				element.find("img").load(function() {
					chatBox.scrollTop(chatBox.prop("scrollHeight"));
				});
			}
		}
	};
});