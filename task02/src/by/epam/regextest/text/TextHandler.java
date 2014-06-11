package by.epam.regextest.text;

import java.util.List;
import java.util.LinkedList;

import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class TextHandler {
	
	public static List<Sentence> getSentencesFromText(Text text) {
		
		List<Sentence> list = new LinkedList<Sentence>();
		
		for (Component p : text.getParts()) {
			if (p.getClass() == Paragraph.class) {
				
				for (Component sent : ((Paragraph)p).getParts()) {
					if (sent.getClass() == Sentence.class) {
						list.add((Sentence)sent);
						
					}
				}
			}
		}
		return list;
	}
	
	public static List<Word> getWordsFromSentence(Sentence sentence) {
		
		List<Word> list = new LinkedList<Word>();
		
		for (Component word : sentence.getParts()) {
			if (word.getClass() == Word.class) {
				list.add((Word)word);
			}
		}
		return list;
	}
	
	public static List<Word> getWordsFromText(Text text) {
		
		List<Word> list = new LinkedList<Word>();
		
		for (Sentence sent : getSentencesFromText(text)) {
			list.addAll(getWordsFromSentence(sent));
		}
		return list;
	}
	
	public static String writeFileIntoString(String filename) {
		
		File file = new File(filename);
		String str = null;
		
		try {
			if (file.length() != 0) {
				char[] charBuf = new char[(int) file.length()];
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"CP1251");
				final int read = isr.read(charBuf);
				str = new String(charBuf, 0, read);
				isr.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
}
