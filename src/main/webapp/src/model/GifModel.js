function GifModel(giphyObj) {
	this.giphyObj = giphyObj;

	this.getHtml = function() {
		return "<img class=\"item\" src='" + giphyObj.images.fixed_height.url + "' />";
	};
}