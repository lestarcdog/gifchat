app.directive("chatMessage", function() {
	return {
		restrict : "E",
		scope : {
			message : "="
		},
		templateUrl : "src/template/chatMessageTemplate.html",
	};
});