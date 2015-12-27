package hu.cdog.gifchat.gifgenerator;

import java.util.Arrays;
import java.util.List;

public class MessageTokinezer {

	private final List<String> tokens;

	private static final List<String> GENERALS = Arrays.asList("and", "or", "I");

	private int idx = 0;

	public MessageTokinezer(String message) {
		String noSpace = message.replaceAll("\\s+", " ");
		tokens = Arrays.asList(noSpace.split(" "));

		for (int i = 0; i < tokens.size(); i++) {
			tokens.add(i, tokens.get(i).toLowerCase());
		}
		tokens.removeAll(GENERALS);
	}

	/**
	 * Returns null on no new keywords
	 * 
	 * @return null or valid keyword
	 */
	public String getNextToken() {
		if (idx >= tokens.size()) {
			return null;
		}
		String ret = tokens.get(idx);
		idx = idx + 1;
		return ret;
	}

}
