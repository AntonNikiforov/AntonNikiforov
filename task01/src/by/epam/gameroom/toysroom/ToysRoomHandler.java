package by.epam.gameroom.toysroom;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.creator.ToyCreator;
import by.epam.gameroom.creator.CarCreator;
import by.epam.gameroom.creator.BallCreator;
import by.epam.gameroom.creator.PlushToyCreator;

public class ToysRoomHandler {
	public static List<Toy> searchByRangeOfCost(ToysRoom room,
												int minCost,
												int maxCost) {
		List<Toy> toys = new ArrayList<>();
		if (maxCost - minCost >= 0) {
			for (Toy toy : room) {
				if (minCost <= toy.getCost() && toy.getCost() <= maxCost) {
					toys.add(toy);
				}
			}
		}
		return toys;
	}
	
	public static ToysRoom createToysRoom(AgeCategory age, int balance) {
		if (balance < 0) {
			throw new IllegalArgumentException();
		}
		
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
		
		return room;
	}
}
