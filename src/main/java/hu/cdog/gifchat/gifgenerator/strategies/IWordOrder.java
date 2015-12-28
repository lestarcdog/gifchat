package hu.cdog.gifchat.gifgenerator.strategies;

import java.util.List;

@FunctionalInterface
public interface IWordOrder {

	public void order(List<String> words);
}
