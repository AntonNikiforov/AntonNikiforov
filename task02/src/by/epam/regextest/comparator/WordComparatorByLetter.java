package by.epam.regextest.comparator;

import java.util.Comparator;

import by.epam.regextest.text.Word;

public class WordComparatorByLetter implements Comparator<Word> {
		
		private char c;
		
		public WordComparatorByLetter(char c) {
			this.c = c;
		}
		
		public int compare(Word w1, Word w2) {
			int res = Integer.compare(count(w1), count(w2));
			return res == 0 ? w1.getText().compareToIgnoreCase(w2.getText()) : res;
		}
		
		private int count(Word w) {
			int count = 0;
			String str = w.getText().toLowerCase();
			int index = str.indexOf(c);
			
			while (index >= 0) {
				count++;
				index = str.indexOf(c, index+1);
			}
			return count;
		}
	}
