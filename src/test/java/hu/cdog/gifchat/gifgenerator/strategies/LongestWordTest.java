package hu.cdog.gifchat.gifgenerator.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LongestWordTest {

	@Test
	public void longestOrder() {
		List<String> arr = new ArrayList<>();
		arr.addAll(Arrays.asList("aa", "bbb", "ccccc"));
		LongestWordFirst.get().order(arr);

		Assert.assertEquals("ccccc", arr.get(0));
		Assert.assertEquals("bbb", arr.get(1));
		Assert.assertEquals("aa", arr.get(2));
	}

}
