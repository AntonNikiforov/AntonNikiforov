package by.epam.regextest.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import by.epam.regextest.text.Word;

public class WordParser extends Parser {
	
	public WordParser(Parser p, String r) {
		super(p, r);
	}
	
	@Override
	public Word defineComponent(String text) {
		
		Word word = new Word();
		
		boolean digit;
		boolean decomposite;
		
		digit = Pattern.matches("\\d+", text);
		decomposite = Pattern.matches("^\\w+'\\w*$", text);
		
		word.setDecomposite(decomposite);
		word.setDigit(digit);
		
		return word;
	}
}
