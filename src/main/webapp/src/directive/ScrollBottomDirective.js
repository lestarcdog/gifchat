app.directive("scrollBottom", [ "$timeout", function($timeout) {

	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$index == scope.loadedImagesSize && SCROLL_BAR_MODE.LOADING == scope.getScrollMode()) {
				var chatBox = $("#chatBox");
				$timeout(function() {
					chatBox.scrollTop(element.offset().top);
				}, 500);

			}

			if (scope.$last && SCROLL_BAR_MODE.NEWEST == scope.getScrollMode()) {
				var chatBox = $("#chatBox");
				$timeout(function() {
					chatBox.scrollTop(chatBox.prop("scrollHeight"));
				}, 500);
			}
		}
	};
} ]);