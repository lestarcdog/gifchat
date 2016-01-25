package hu.cdog.gifchat.model.dto;

public class ExceptionDto extends BaseDto {

	@Override
	public String toString() {
		return "ExceptionDto [message=" + message + "]";
	}

	private String message;
	private final Boolean error = Boolean.TRUE;

	public ExceptionDto() {

	}

	public ExceptionDto(Exception e) {
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getError() {
		return error;
	}

}
