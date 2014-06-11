package by.epam.regextest.text;

abstract public class Component implements Comparable<Component> {
	
	abstract public String getText();
	
	@Override
	public int compareTo(Component obj) {
		return getText().compareTo(obj.getText());
	}
}
