package by.epam.gameroom.main;

import java.util.List;
import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toysroom.ToysRoom;
import by.epam.gameroom.toysroom.ToysRoomHandler;
import by.epam.gameroom.toysroom.AgeCategory;

public class Main {
	public static void main(String[] args) {
		
		// fill the room with toys
		int balance = 1_000_000;
		ToysRoom room = new ToysRoom();
		ToyCreator[] creators = {
									new CarCreator(),
									new BallCreator(),
									new PlushToyCreator(),
								};
		Random random = new Random();
		do {
			// index of random creator
			int index = random.nextInt(creators.length);
			Toy toy = creators[index].factoryMethod();
			
			balance -= toy.getCost();
			// it is necessary to check to not go into debt
			if (balance < 0) {
				break;
			} 
			room.addToy(toy);
		} while (balance > 0); // while (true);
	
		
		ToysRoomHandler.sortToysByCost(room);
		
		for (Toy toy : room.getToysList()) {
			System.out.println(toy);
		}
		System.out.println(ToysRoomHandler.getNumberOfToys(room));
		
		System.out.println("-----------");
		
		List<Toy> toys =
			ToysRoomHandler.searchByRangeOfCost(room, 50_000, 60_000);
			
		for (Toy toy : toys) {
			System.out.println(toy);
		}
		System.out.println(toys.size());
	}
}
