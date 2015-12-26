package hu.cdog.gifchat.model;

public class GifMessage {
	private String username;
	private String userText;
	private String gifTextPath;

	public GifMessage() {

	}

	public GifMessage(String username, String userText, String gifTextPath) {
		super();
		this.username = username;
		this.userText = userText;
		this.gifTextPath = gifTextPath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}

	public String getGifTextPath() {
		return gifTextPath;
	}

	public void setGifTextPath(String gifTextPath) {
		this.gifTextPath = gifTextPath;
	}

}
