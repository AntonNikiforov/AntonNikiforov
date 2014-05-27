package by.epam.gameroom.toy;

import java.util.Comparator;

import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Material;

public abstract class Toy {
	
	private Size size;
	private Color color;
	private Material material;
	private int cost;
	
	public Toy() {}
	
	public Toy(Size size, Color color, Material material, int cost) {
		if (cost < 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
		this.color = color;
		this.material = material;
		this.cost = cost;
	}
	
	public void setSize(Size size) {
		this.size = size;
	}
	
	public Size getSize() {
		return size;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setCost(int cost) {
		if (cost < 0) {
			throw new IllegalArgumentException();
		}
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
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
		
		Toy toy = (Toy) obj;
		
		/* can i write this way?
		if (size != toy.getSize() ||
			color != toy.getColor() ||
			material != toy.getMaterial() ||
			cost != toy.getCost())
				return false;
		*/
		if (size != toy.getSize()) {
			return false;
		}
		if (color != toy.getColor()) {
			return false;
		}
		if (material != toy.getMaterial()) {
			return false;
		}
		if (cost != toy.getCost()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + size.ordinal();
        result = prime * result + color.ordinal();
        result = prime * result + material.ordinal();
        result = prime * result + cost;
        return result;
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		
		str.append(getClass().getName())
			.append(", size: ").append(size)
			.append(", color: ").append(color)
			.append(", material: ").append(material)
			.append(", cost: ").append(cost);
		
		return str.toString();
	}
    
    public static class SizeComparator implements Comparator<Toy> {
		public int compare(Toy t1, Toy t2) {
			return Integer.compare(t1.getSize().ordinal(),
									t2.getSize().ordinal());
		}
	}
	
	public static class CostComparator implements Comparator<Toy> {
		public int compare(Toy t1, Toy t2) {
			return Integer.compare(t1.getCost(), t2.getCost());
		}
	}
}
