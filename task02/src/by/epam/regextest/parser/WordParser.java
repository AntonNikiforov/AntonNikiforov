package by.epam.regextest.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import by.epam.regextest.text.Component;
import by.epam.regextest.text.Word;
import by.epam.regextest.text.Letter;
import by.epam.regextest.text.Separator;

public class WordParser extends Parser {
	
	public Word parse(String text) {
		
		Word word = new Word();
		Component separator;
		Component letter;
		
		Pattern p = Pattern.compile("[^\\W\\s]");
		Matcher m = p.matcher(text);
		
		int last_match = 0; 
		
		while (m.find()) {
			
			separator = new Separator(text.substring(last_match, m.start())); 
			word.addPart(separator);
			//letter = nextParser.parse();
			word.addPart(new Letter(m.group()));
			
			last_match = m.end(); 
		} 
		word.addPart(new Separator(text.substring(last_match)));
		
		boolean digit;
		boolean decomposite;
		
		digit = p.matches("^\\d$", text);
		decomposite = p.matches("^\\w+'\\w*$", text);
		
		word.setDecomposite(decomposite);
		word.setDigit(digit);
		
		return word;
	}
}
