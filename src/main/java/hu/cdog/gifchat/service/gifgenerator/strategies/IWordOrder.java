package hu.cdog.gifchat.service.gifgenerator.strategies;

import java.util.List;

@FunctionalInterface
public interface IWordOrder {

	public void order(List<String> words);
}
