app.directive("scrollBottom", function() {
	
	return {
		restrict : "A",
		link : function(scope, element, attrs) {
			if(scope.$index == parseInt(attrs.loadedImagesSize)) {
				element.find("img").load(function() {
					var chatBox = $("#chatBox");
					chatBox.scrollTop(element.offset().top);
				});
			}
			
			if (scope.$last) {
				console.log("scrollLast");
				element.find("img").load(function() {
					var chatBox = $("#chatBox");
					chatBox.scrollTop(chatBox.prop("scrollHeight"));
				});
			}
		}
	};
});