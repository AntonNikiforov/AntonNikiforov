package by.epam.gameroom.toysroom;

import java.util.List;
import java.util.ArrayList;

import by.epam.gameroom.toy.Toy;

public class ToysRoom {
	
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
	
	public List<Toy> getToysList() {
        return toysList;
    }
}
