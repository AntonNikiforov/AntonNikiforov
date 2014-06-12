package by.epam.regextest.text;

public class Sentence extends Text {
	
	public enum SentenceType {
		DECLERATIVE,
		INTERROGATIVE,
		EXCLAMATORY;
	}
	
	private SentenceType type;
	
	public void setSentenceType(SentenceType type) {
		this.type = type;
	}
	
	public SentenceType getSentenceType() {
		return type;
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
		
		Sentence sent = (Sentence) obj;
		
		if (type != sent.getSentenceType()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + type.ordinal();
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		
		str.append(", type: ").append(type);
		
		return str.toString();
	}
}
