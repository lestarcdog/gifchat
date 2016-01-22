package hu.cdog.gifchat.service.giphy.strategies;

import java.util.List;

@FunctionalInterface
public interface IWordOrder {

	public void order(List<String> words);
}
