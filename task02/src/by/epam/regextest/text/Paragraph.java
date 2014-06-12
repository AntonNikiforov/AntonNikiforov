package by.epam.regextest.text;

public class Paragraph extends Text {
	
	private boolean head;
	
	public void setHead(boolean head) {
		this.head = head;
	}
	
	public boolean isHead() {
		return head;
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
		
		Paragraph par = (Paragraph) obj;
		
		if (head != par.isHead()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (head ? 1231 : 1237);
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		
		str.append(", head: ").append(head);
		
		return str.toString();
	}
}
