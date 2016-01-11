package hu.cdog.gifchat.memdb;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.GifMessage;
import hu.cdog.gifchat.model.giphy.GifImage;
import hu.cdog.gifchat.model.giphy.GifImageFormats;

public class MemDbTest {

	@Test
	public void onlyStore100Messages() {
		MemDb memDb = new MemDb();
		GifMessage m = dummyGifMessage();
		for (int i = 0; i < GifChatConstants.MAX_SIZE + 10; i++) {
			memDb.add(m);

		}
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getOriginalGifUrls().size());
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getAll().size());
	}

	@Test
	public void getCurrents() {
		MemDb db = new MemDb();
		GifImageFormats gf = new GifImageFormats();
		GifMessage msg = null;
		gf.setOriginal(new GifImage());
		for (int i = 0; i < GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT * 3; i++) {
			msg = new GifMessage("a", String.valueOf(i), "asdf", "asdf", gf);
			db.add(msg);
		}

		List<GifMessage> currents = db.getCurrents();
		Assert.assertEquals(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT, currents.size());

	}

	@Test
	public void returnEarlierMessagesContinsList() throws InterruptedException {
		MemDb db = new MemDb();
		GifMessage msg = null;
		GifImageFormats gf = new GifImageFormats();
		gf.setOriginal(new GifImage());
		for (int i = 0; i < GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT * 3; i++) {
			msg = new GifMessage("a", String.valueOf(i), "asdf", "asdf", gf);
			db.add(msg);
			Thread.sleep(5);
		}

		List<GifMessage> all = db.getAll();

		// full list
		List<GifMessage> full = db.earlierThan(all.get(all.size() - 2).getSentTime());
		Assert.assertEquals(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT, full.size());

		// small list
		List<GifMessage> smallList = db
				.earlierThan(all.get(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT - 2).getSentTime());
		Assert.assertEquals(GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT - 2, smallList.size());

	}

	@Test(expected = UnsupportedOperationException.class)
	public void throwNotImplementedSearch() {
		MemDb db = new MemDb();

		db.earlierThan(LocalDateTime.now());
	}

	private GifMessage dummyGifMessage() {
		GifMessage m = new GifMessage();
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
