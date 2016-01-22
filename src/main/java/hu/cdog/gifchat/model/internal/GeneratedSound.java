package hu.cdog.gifchat.model.internal;

public class GeneratedSound {
	private final byte[] content;
	private final boolean generated;
	private final String message;

	public GeneratedSound(byte[] content, String message) {
		this.content = content;
		this.generated = (content != null);
		this.message = message;
	}

	public byte[] getContent() {
		return content;
	}

	public boolean isGenerated() {
		return generated;
	}

	public String getMessage() {
		return message;
	}

}