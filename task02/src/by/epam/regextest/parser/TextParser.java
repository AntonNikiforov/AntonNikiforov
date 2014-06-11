package by.epam.regextest.parser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import by.epam.regextest.text.Component;
import by.epam.regextest.text.Text;
import by.epam.regextest.text.Separator;

public class TextParser extends Parser {
	
	public Text parse(String text) {
		
		Text parts = new Text();
		Separator separator;
		Component paragraph;
		ParagraphParser parser = new ParagraphParser();
		
		Pattern pattern = Pattern.compile("\\n[^\\n]*\\{.+?\\n\\}\\s*", Pattern.DOTALL);
		Matcher m = pattern.matcher(text);
		
		int last_match = 0; 
		
		while (m.find()) {
			
			paragraph = parser.parse(text.substring(last_match, m.start()));
			parts.addPart(paragraph);
			
			separator = new Separator(m.group());
			separator.setCode(true);
			parts.addPart(separator);
			
			last_match = m.end(); 
		} 
		paragraph = parser.parse(text.substring(last_match));
		parts.addPart(paragraph);
		
		return parts;
	}
}
