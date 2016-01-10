app.directive("chatMessageScroll", function($location) {

	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if (scope.$last) {
				var chatBox = $("#chatBox");
				element.find("img").load(function() {
					chatBox.scrollTop(chatBox.prop("scrollHeight"));
				});

				// setTimeout(function() {
				// var chatBox = $("#chatBox");
				// //console.log("ScrollHeight: "+chatBox.prop("scrollHeight"));
				// chatBox.scrollTop(chatBox.prop("scrollHeight"));
				// }, 1500);

			}
		}
	};
});