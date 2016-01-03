package hu.cdog.gifchat.model.dto;

public class UserMessageScoreDto extends BaseDto {

	private String username;
	private Integer count;

	public UserMessageScoreDto(String username, Integer count) {
		this.username = username;
		this.count = count;
	}

	public UserMessageScoreDto() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "UserMessageScoreDto [username=" + username + ", count=" + count + "]";
	}

}
