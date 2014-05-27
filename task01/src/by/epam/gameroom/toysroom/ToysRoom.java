package by.epam.gameroom.toysroom;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.comparator.CostComparator;

public class ToysRoom implements Iterable<Toy> {
	
	private AgeCategory age;
	private List<Toy> toysList = new ArrayList<>();
	
	public ToysRoom() {}
	
	public ToysRoom(AgeCategory age, ArrayList<Toy> list) {
		this.age = age;
		toysList.addAll(list);
	}
	
	public void setAgeCategory(AgeCategory age) {
		this.age = age;
	}
	
	public AgeCategory getAgeCatogory() {
		return age;
	}
	
	public boolean addToy(Toy toy) {
		return toysList.add(toy);
	}
	
	public List<Toy> getToys() {
        return toysList;
    }
    
    public void sortToysByCost() {
		Collections.sort(toysList, new CostComparator());
	}
	
	public int getNumberOfToys() {
		return toysList.size();
	}
	
	public Iterator<Toy> iterator() {
		return toysList.iterator();
	}
}
