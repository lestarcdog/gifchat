package hu.cdog.gifchat.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hu.cdog.gifchat.model.dto.UserMessageScoreDto;

public class ChatUsersServiceTest {

	@Test
	public void sortOfChatUsersMessageNumber() {
		ChatUsersService s = new ChatUsersService();
		s.addUser("jozsi");
		s.addUser("bela");

		s.updateUserDependentProperties("jozsi");
		s.updateUserDependentProperties("jozsi");
		s.updateUserDependentProperties("jozsi");
		s.updateUserDependentProperties("bela");

		List<UserMessageScoreDto> list = s.getTopUsersMessageScore();

		Assert.assertEquals("jozsi", list.get(0).getUsername());
		Assert.assertEquals(((Integer) 3), list.get(0).getCount());

		list = s.getLowestUsersMessageScore();

		Assert.assertEquals("bela", list.get(0).getUsername());
		Assert.assertEquals(((Integer) 1), list.get(0).getCount());

	}

}
