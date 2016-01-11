package hu.cdog.gifchat.service.gifgenerator.strategies;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LongestWordFirst implements IWordOrder {

	public static final LongestWordFirst INSTANCE = new LongestWordFirst();
	public static final Comparator<String> COMP = (s1, s2) -> s1.length() < s2.length() ? -1
			: s1.length() > s2.length() ? 1 : 0;
	public static final Comparator<String> COMP_REVERSE = COMP.reversed();

	protected LongestWordFirst() {
	}

	@Override
	public void order(List<String> words) {
		Collections.sort(words, COMP_REVERSE);
	}

	public static IWordOrder get() {
		return INSTANCE;
	}

}
