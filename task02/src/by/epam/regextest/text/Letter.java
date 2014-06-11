package by.epam.regextest.text;

public class Letter extends Leaf {
	
	private boolean voval;
	private boolean capital; 
	
	public Letter(String text) {
		super(text);
	}
	
	public void setVoval(boolean voval) {
		this.voval = voval;
	}
	
	public boolean isVoval() {
		return voval;
	}
	
	public void setCapital(boolean capital) {
		this.capital = capital;
	}
	
	public boolean isCapital() {
		return capital;
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
		
		Letter letter = (Letter) obj;
		
		if (voval != letter.isVoval()) {
			return false;
		}
		if (capital != letter.isCapital()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (voval ? 1231 : 1237);
        result = prime * result + (capital ? 1231 : 1237);
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		
		str.append(getClass().getName())
			.append(", voval: ").append(voval)
			.append(", capital: ").append(capital);
		
		return str.toString();
	}
}
