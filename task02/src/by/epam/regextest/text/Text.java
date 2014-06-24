package by.epam.regextest.text;

import java.util.List;
import java.util.LinkedList;

public class Text extends Component {
	
	List<Component> parts = new LinkedList<Component>();
	
	public void addPart(Component part) {
		parts.add(part);
	}
	
	public List<Component> getParts() {
		return parts;
	}
	
	public String constructTextOfTheParts() {
		StringBuilder text = new StringBuilder();
		
		for (Component part : parts) {
			text.append(part.constructTextOfTheParts());
		}
		return text.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (! super.equals(obj)) {
			return false;
		}
		
		Text text = (Text) obj;
		
		if (!parts.equals(text.getParts())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        for (Component part : parts) {	
			result = prime * result + part.hashCode();
		}
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(getClass().getName()).append(" [");
		for (Component part : parts) {	
			str.append(", ").append(part.constructTextOfTheParts());
		}
		str.append("]");
		
		return str.toString();
	}
}
