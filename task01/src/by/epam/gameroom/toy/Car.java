package by.epam.gameroom.toy;

import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Material;
import by.epam.gameroom.toy.characteristic.CarType;

public class Car extends Toy {
	
	private CarType carType;
	
	public Car() {}
	
	public Car(Size size, Color color, int cost, CarType type) {
		super(size, color, Material.PLASTIC, cost);
		carType = type;
	}
	
	public void setCarType(CarType type) {
		carType = type;
	}
	
	public CarType getCarType() {
		return carType;
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
		
		Car car = (Car) obj;
		
		if (carType != car.getCarType()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + carType.ordinal();
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		return str.append(", car type: ").append(carType).toString();
	}
}
