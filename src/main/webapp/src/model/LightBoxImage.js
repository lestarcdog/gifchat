function LightBoxImage(gifMessage) {
	this.url = gifMessage.gifFixedHeight.url;
	this.largeUrl = gifMessage.gifOriginal.url;
	this.caption = gifMessage.keyword;
	this.largeHeight = gifMessage.gifOriginal.height;
	this.largeWidth = gifMessage.gifOriginal.width;
}