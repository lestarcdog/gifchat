package hu.cdog.gifchat.model.dto;

import hu.cdog.gifchat.exception.GifChatException;

public class ExceptionDto extends BaseDto {

	@Override
	public String toString() {
		return "ExceptionDto [message=" + message + "]";
	}

	private String message;

	public ExceptionDto() {

	}

	public ExceptionDto(GifChatException e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
