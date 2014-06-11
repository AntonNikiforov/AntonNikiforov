package by.epam.regextest.text;

public class Separator extends Leaf {
	
	boolean code;
	
	public Separator(String text) {
		super(text);
	}
	
	public void setCode(boolean code) {
		this.code = code;
	}
	
	public boolean isCode() {
		return code;
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
		
		Separator sep = (Separator) obj;
		
		if (code != sep.isCode()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (code ? 1231 : 1237);
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(getClass().getName())
			.append(", code: ").append(code);
		
		return str.toString();
	}
}
