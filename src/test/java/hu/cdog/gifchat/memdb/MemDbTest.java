package hu.cdog.gifchat.memdb;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.data.MessageCache;
import hu.cdog.gifchat.model.entities.UserMessage;
import hu.cdog.gifchat.model.giphy.GifImage;
import hu.cdog.gifchat.model.giphy.GifImageFormats;

public class MemDbTest {

	@Test
	public void onlyStore100Messages() {
		MessageCache memDb = new MessageCache();
		UserMessage m = dummyGifMessage();
		for (int i = 0; i < GifChatConstants.MAX_SIZE + 10; i++) {
			memDb.add(m);

		}
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getLastGifs(10).size());
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getAll().size());
	}

	@Test
	public void getCurrents() {
		MessageCache db = new MessageCache();
		GifImageFormats gf = new GifImageFormats();
		UserMessage msg = null;
		gf.setOriginal(new GifImage());
		for (int i = 0; i < GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT * 3; i++) {
			msg = new UserMessage("a", String.valueOf(i), "asdf", "asdf", gf);
			db.add(msg);
		}

		List<UserMessage> currents = db.getCurrents();
		Assert.assertEquals(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT, currents.size());

	}

	@Test
	public void returnEarlierMessagesContinsList() throws InterruptedException {
		MessageCache db = new MessageCache();
		UserMessage msg = null;
		GifImageFormats gf = new GifImageFormats();
		gf.setOriginal(new GifImage());
		for (int i = 0; i < GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT * 3; i++) {
			msg = new UserMessage("a", String.valueOf(i), "asdf", "asdf", gf);
			db.add(msg);
			Thread.sleep(5);
		}

		List<UserMessage> all = db.getAll();

		// full list
		List<UserMessage> full = db.earlierThan(all.get(all.size() - 2).getSentTime());
		Assert.assertEquals(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT, full.size());

		// small list
		List<UserMessage> smallList = db
				.earlierThan(all.get(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT - 2).getSentTime());
		Assert.assertEquals(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT - 2, smallList.size());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void throwNotImplementedSearch() {
		MessageCache db = new MessageCache();

		db.earlierThan(LocalDateTime.now());
	}

	private UserMessage dummyGifMessage() {
		UserMessage m = new UserMessage();
		m.setKeyword("keyword");
		m.setUsername("justin bieber");
		m.setUserText("asdf");
		m.setOriginalImage(new GifImage());

		return m;
	}

	// @Test
	public void a() {
		LocalDateTime now = LocalDateTime.now(GifChatConstants.UTC_ZONE);
		System.out.println(now);
		long epochMilli = now.toInstant(ZoneOffset.UTC).toEpochMilli();
		System.out.println(epochMilli);

		Instant ofEpochMilli = Instant.ofEpochMilli(epochMilli);
		LocalDateTime back = LocalDateTime.ofInstant(ofEpochMilli, GifChatConstants.UTC_ZONE);
		System.out.println(back);
	}

}
