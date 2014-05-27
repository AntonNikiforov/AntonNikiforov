package by.epam.gameroom.creator;

import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Animal;
import by.epam.gameroom.toy.PlushToy;

public class PlushToyCreator extends ToyCreator {
    @Override
    public Toy factoryMethod() {
		Random rand = new Random();
		
		Size size = Size.randomSize();
		Color color = Color.randomColor();
		int cost = rand.nextInt(90_000) + 10_000;
		Animal animal = Animal.randomAnimal();
		boolean talking = rand.nextBoolean();
		
		return new PlushToy(size, color, cost, animal, talking);
	}
}
