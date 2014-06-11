package by.epam.regextest.comparator;

import java.util.Comparator;
import java.util.List;

import by.epam.regextest.text.Sentence;
import by.epam.regextest.text.Word;
import by.epam.regextest.text.TextHandler;

public class SentenceComparator implements Comparator<Sentence> {
		
	public int compare(Sentence s1, Sentence s2) {
		List<Word> words1 = TextHandler.getWordsFromSentence(s1);
		List<Word> words2 = TextHandler.getWordsFromSentence(s2);
		return Integer.compare(words1.size(), words2.size());
	}
}
