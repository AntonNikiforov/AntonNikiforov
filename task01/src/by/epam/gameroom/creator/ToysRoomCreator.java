package by.epam.gameroom.creator;

import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toysroom.ToysRoom;
import by.epam.gameroom.toysroom.AgeCategory;
import by.epam.gameroom.creator.ToyCreator;
import by.epam.gameroom.creator.CarCreator;
import by.epam.gameroom.creator.BallCreator;
import by.epam.gameroom.creator.PlushToyCreator;

public class ToysRoomCreator {
	
	public static ToysRoom createRoomByBalance(AgeCategory age, int balance) {
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
	
	public static ToysRoom createRoomByNumber(AgeCategory age, int numberOfToys) {
		if (numberOfToys < 0) {
			throw new IllegalArgumentException();
		}

		ToysRoom room = new ToysRoom();
		ToyCreator[] creators = {
									new CarCreator(),
									new BallCreator(),
									new PlushToyCreator(),
								};
		Random random = new Random();
		
		while (numberOfToys > 0) {
			// index of random creator
			int index = random.nextInt(creators.length);
			Toy toy = creators[index].factoryMethod();

			numberOfToys--;
			room.addToy(toy);
		}

		return room;
	}
}
