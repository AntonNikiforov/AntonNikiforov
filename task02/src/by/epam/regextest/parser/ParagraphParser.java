package by.epam.regextest.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import by.epam.regextest.text.Component;
import by.epam.regextest.text.Paragraph;
import by.epam.regextest.text.Separator;

public class ParagraphParser extends Parser {
	
	public Paragraph parse(String text) {
		
		Paragraph p = new Paragraph();
		Component separator;
		Component sentence;
		SentenceParser parser = new SentenceParser();
		
		Pattern pattern = Pattern.compile("[A-Z][^.!?\\n]+[.!?\\n]");
		Matcher m = pattern.matcher(text);
		
		int last_match = 0; 
		
		while (m.find()) {
			
			separator = new Separator(text.substring(last_match, m.start())); 
			p.addPart(separator);
			
			sentence = parser.parse(m.group());
			p.addPart(sentence);
			
			last_match = m.end(); 
		} 
		p.addPart(new Separator(text.substring(last_match)));
		
		return p;
	}
}
