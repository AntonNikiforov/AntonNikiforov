package by.epam.gameroom.main;

import java.util.List;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toysroom.ToysRoom;
import by.epam.gameroom.toysroom.ToysRoomHandler;
import by.epam.gameroom.toysroom.AgeCategory;

public class Main {
	public static void main(String[] args) {
		
		int balance = 1_000_000;
		ToysRoom room =
			ToysRoomHandler.createToysRoom(AgeCategory.CHILD, balance);
		
		room.sortToysByCost();
		
		for (Toy toy : room) {
			System.out.println(toy);
		}
		System.out.println(room.getNumberOfToys());
		
		System.out.println("-----------");
		
		List<Toy> toys =
			ToysRoomHandler.searchByRangeOfCost(room, 50_000, 60_000);
			
		for (Toy toy : toys) {
			System.out.println(toy);
		}
		System.out.println(toys.size());
	}
}
