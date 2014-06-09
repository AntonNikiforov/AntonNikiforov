package by.epam.gameroom.toy;

import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Material;

public class Ball extends Toy {
	
	private float diametr;
	private float pressure;
	
	public Ball() {}
	
	public Ball(Color color, int cost, float diametr, float pressure) {

		super(Size.MIDSIZE, color, Material.RUBBER, cost);
		
		if (diametr <= 0.0f || pressure < 0.0f) {
			throw new IllegalArgumentException();
		}
		this.diametr = diametr;
		this.pressure = pressure;
	}
	
	public void setDiametr(float diametr) {
		
		if (diametr <= 0.0f) {
			throw new IllegalArgumentException();
		}
		this.diametr = diametr;
	}
	
	public float getDiametr() {
		return diametr;
	}
	
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	
	public float getPressure() {
		return pressure;
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
		
		Ball ball = (Ball) obj;
		
		if (diametr != ball.getDiametr()) {
			return false;
		}
		if (pressure != ball.getPressure()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) diametr;
        result = prime * result + (int) pressure;
        return result;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(super.toString());
		
		str.append(", diamert: ").append(diametr)
			.append(", pressure: ").append(pressure);
		
		return str.toString();
	}
}
