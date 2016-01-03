package hu.cdog.gifchat.model.dto;

public class UserMessageDto extends BaseDto {
	@Override
	public String toString() {
		return "UserMessageDto [message=" + message + "]";
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
