package hu.cdog.gifchat.memdb;

import java.time.LocalDateTime;
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
		GifMessage m = new GifMessage();
		for (int i = 0; i < GifChatConstants.MAX_SIZE + 10; i++) {
			memDb.add(m);
			;
		}
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getOriginalGifUrls().size());
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getAll().size());
	}

	@Test
	public void returnEarlierMessagesContinsList() {
		MemDb db = new MemDb();

		GifImageFormats gf = new GifImageFormats();
		GifMessage msg = null;
		gf.setOriginal(new GifImage());
		for (int i = 0; i < GifChatConstants.CURRENT_IMAGE_RETURN_LIMIT * 3; i++) {
			msg = new GifMessage("a", String.valueOf(i), "asdf", gf);
			db.add(msg);
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

}
