package by.epam.regextest.text;

public class Word extends Text {
	
	private boolean decomposite;
	private boolean digit;
	
	public void setDecomposite(boolean decomposite) {
		this.decomposite = decomposite;
	}
	
	public boolean isDecomposite() {
		return decomposite;
	}
	
	public void setDigit(boolean digit) {
		this.digit = digit;
	}
	
	public boolean isDigit() {
		return digit;
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
		
		Word word = (Word) obj;
		
		if (decomposite != word.isDecomposite()) {
			return false;
		}
		if (digit != word.isDigit()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (decomposite ? 1231 : 1237);
        result = prime * result + (digit ? 1231 : 1237);
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		
		str.append(", decomposite: ").append(decomposite);
		str.append(", digit: ").append(decomposite);
		
		return str.toString();
	}
}
