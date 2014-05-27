package by.epam.gameroom.toy;

import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Material;
import by.epam.gameroom.toy.characteristic.Animal;

public class PlushToy extends Toy {
	
	private Animal animal;
	private boolean talking;
	
	public PlushToy() {}
	
	public PlushToy(Size size, Color color, int cost, Animal animal, boolean isTalking) {
		super(size, color, Material.FABRIC, cost);
		this.animal = animal;
		talking = isTalking;
	}
	
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	public Animal getAnimal() {
		return animal;
	}
	
	public void setTalking(boolean isTalking) {
		talking = isTalking;
	}
	
	public boolean isTalking() {
		return talking;
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
		
		PlushToy toy = (PlushToy) obj;
		
		if (animal != toy.getAnimal()) {
			return false;
		}
		if (talking != toy.isTalking()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + animal.ordinal();
        result = prime * result + (talking ? 1231 : 1237);
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		
		str.append(", animal: ").append(animal)
			.append(", is talking: ").append(talking);
		
		return str.toString();
	}
}
