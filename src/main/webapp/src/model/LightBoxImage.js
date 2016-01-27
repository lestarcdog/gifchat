function LightBoxImage(gifMessage) {
	this.url = gifMessage.gifFixedHeightUrl;
	this.largeUrl = gifMessage.gifOriginalUrl;
	this.caption = gifMessage.keyword;
	this.largeHeight = gifMessage.gifOriginalHeight;
	this.largeWidth = gifMessage.gifOriginalWidth;
	this.original = gifMessage;
}