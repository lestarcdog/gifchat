var app = angular.module("GifApp", []);

app.constant("AppConstants", {
	"api" : "&api_key=dc6zaTOxFJmzC"
});

app.controller("MainController", function($scope, GiphyService) {
	var chatBox = angular.element("#chatBox");
	$scope.sending = false;
	$scope.search = function() {
		$scope.sending = true;
		GiphyService.get($scope.txt).success(function(data, status, headers, config) {
			var gifs = data.data;
			console.log(gifs);
			// chatBox.append(new GifText(gifs[0]).getHtml());
			chatBox.append(new GifText(gifs[0]).getHtml());
			$scope.sending = false;
		});
	};
});

function GifText(giphyObj) {
	this.giphyObj = giphyObj;

	this.getHtml = function() {
		return "<img class=\"item\" src='" + giphyObj.images.fixed_height.url + "' />";
	};
}
