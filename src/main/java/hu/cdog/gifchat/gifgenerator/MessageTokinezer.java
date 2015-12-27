package hu.cdog.gifchat.gifgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.cdog.gifchat.GifChatConstants;

public class MessageTokinezer {

	private List<String> tokens;

	private static final List<String> GENERALS = Arrays.asList("and", "or", "i", "is", "a", "an", "the", "this", "that",
			"you", "but");

	private int idx = 0;
	private final String message;

	public MessageTokinezer(String message) {
		this.message = message;
		String noSpace = message.replaceAll("\\s+", " ");
		tokens = new ArrayList<>(Arrays.asList(noSpace.split(" ")));

		if (tokens.size() < GifChatConstants._SENTENCE_SIZE) {
			tokens = Arrays.asList(noSpace.toLowerCase());
		} else {
			tokens = tokenizeSentence(tokens);
		}
	}

	private List<String> tokenizeSentence(List<String> unformatted) {
		int size = unformatted.size();
		List<String> formatted = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			String lowerCase = unformatted.get(i).toLowerCase();
			lowerCase = lowerCase.replaceAll("[^\\w]", "");
			formatted.add(lowerCase);
		}
		formatted.removeAll(GENERALS);
		return formatted;
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
