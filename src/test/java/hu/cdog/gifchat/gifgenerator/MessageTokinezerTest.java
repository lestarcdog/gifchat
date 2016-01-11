package hu.cdog.gifchat.gifgenerator;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import hu.cdog.gifchat.service.gifgenerator.MessageTokinezer;
import hu.cdog.gifchat.service.gifgenerator.strategies.LongestWordFirst;

public class MessageTokinezerTest {

	@Test
	public void tokenizeWords() {
		MessageTokinezer tokinezer = new MessageTokinezer("This is a     TEst SenTENCE!", LongestWordFirst.get());
		int count = 0;
		String result = null;
		do {
			result = tokinezer.getNextToken();
			Assert.assertThat(result, CoreMatchers.anyOf(CoreMatchers.is("this"), CoreMatchers.is("test"),
					CoreMatchers.is("sentence"), CoreMatchers.nullValue()));
			count++;
		} while (result != null);

		Assert.assertEquals(3, count);
	}

}
