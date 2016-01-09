function LightBoxImage(gifMessage) {
	this.url = gifMessage.gifFixedHeight.url;
	this.largeUrl = gifMessage.gifOriginal.url;
	this.caption = gifMessage.keyword;
}