app.directive("scrollBottom", [ "$timeout", function($timeout) {

	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$index === scope.loadedImagesSize && SCROLL_BAR_MODE.LOADING === scope.getScrollMode()) {
				$timeout(function() {
					scope.scrollToPos(element.offset().top,false);
				}, 500);

			}

			if (scope.$last && SCROLL_BAR_MODE.NEWEST === scope.getScrollMode()) {
				$timeout(function() {
					scope.scrollToPos(null,false);
				}, 500);
			}
		}
	};
} ]);