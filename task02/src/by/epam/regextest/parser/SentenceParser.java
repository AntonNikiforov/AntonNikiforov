package by.epam.regextest.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import by.epam.regextest.text.Component;
import by.epam.regextest.text.Sentence;
import by.epam.regextest.text.Separator;

public class SentenceParser extends Parser {
	
	public Sentence parse(String text) {
		
		Sentence sentence = new Sentence();
		Component separator;
		Component word;
		WordParser parser = new WordParser();
		
		Pattern p = Pattern.compile("(\\b[^\\W\\s]+[']*\\b)+");
		Matcher m = p.matcher(text);
		
		int last_match = 0; 
		
		while (m.find()) {
			
			separator = new Separator(text.substring(last_match, m.start())); 
			sentence.addPart(separator);
			
			word = parser.parse(m.group());
			sentence.addPart(word);
			
			last_match = m.end(); 
		} 
		sentence.addPart(new Separator(text.substring(last_match)));
		
		Sentence.SentenceType type = Sentence.SentenceType.DECLERATIVE;
		
		if (p.matches("\\?$", text)) {
			type = Sentence.SentenceType.INTERROGATIVE;
		} else
		if (p.matches("!$", text)) {
			type = Sentence.SentenceType.EXCLAMATORY;
		}
		
		sentence.setSentenceType(type);
		
		return sentence;
	}
}
