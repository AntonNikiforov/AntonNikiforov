package by.epam.regextest.task;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

import by.epam.regextest.text.Text;
import by.epam.regextest.text.Sentence;
import by.epam.regextest.text.Word;
import by.epam.regextest.comparator.SentenceComparator;
import by.epam.regextest.comparator.WordComparator;
import by.epam.regextest.comparator.WordComparatorByLetter;

public class Main {
	public static void main(String[] args) {
		
		String str = TextHandler.writeFileIntoString("task2text.txt");
		
		Text text = TextHandler.getTextFromString(str);
		
		System.out.println("---------------");
		
		// 2. Вывести все предложения заданного текста в порядке возрастания
		// количества слов в каждом из них.
		List<Sentence> sList = TextHandler.getSentencesFromText(text);
		Collections.sort(sList, new SentenceComparator());
		
		for (Sentence s : sList) {
			System.out.println(s.getText());
		}
		System.out.println("---------------");
		
		// 6. Напечатать слова текста в алфавитном порядке по первой букве.
		// Слова, начинающиеся с новой буквы, печатать с красной строки.
		List<Word> wList = TextHandler.getWordsFromText(text);
		Collections.sort(wList, new WordComparator());
		
		Character c = 'a';
		for (Word w : wList) {
			if (w.isDigit()) continue;
			Character first = Character.toLowerCase(w.getText().charAt(0));
			if (c != first) {
				System.out.println();
				c = first;
			}
			System.out.print(w.getText() + " ");
		}
		System.out.println("\n---------------");
		
		// 9. Все слова текста рассортировать по возрастанию количества заданной буквы в слове.
		// Слова с одинаковым количеством букв расположить в алфавитном порядке.
		Collections.sort(wList, new WordComparatorByLetter('s'));
		
		for (Word w : wList) {
			if (w.isDigit()) continue;
			System.out.println(w.getText());
		}
		System.out.println("---------------");
		
		System.out.println(text.getText());
		System.out.println("---------------");
	}
}
