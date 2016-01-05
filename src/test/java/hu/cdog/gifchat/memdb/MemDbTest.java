package hu.cdog.gifchat.memdb;

import org.junit.Assert;
import org.junit.Test;

import hu.cdog.gifchat.GifChatConstants;
import hu.cdog.gifchat.model.GifMessage;

public class MemDbTest {

	@Test
	public void onlyStore100Messages() {
		MemDb memDb = new MemDb();
		GifMessage m = new GifMessage();
		for(int i=0;i<GifChatConstants.MAX_SIZE + 10; i++) {
			memDb.add(m);;
		}
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getOriginalGifUrls().size());
		Assert.assertEquals(GifChatConstants.MAX_SIZE, memDb.getAll().size());
	}

}
