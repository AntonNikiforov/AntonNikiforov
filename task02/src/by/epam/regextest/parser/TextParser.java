package by.epam.regextest.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import by.epam.regextest.text.Text;

public class TextParser extends Parser {
	
	public TextParser(Parser p, String r) {
		super(p, r);
	}
	
	@Override
	public Text defineComponent(String text) {
		return new Text();
	}
}
