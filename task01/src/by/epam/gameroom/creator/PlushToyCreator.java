package by.epam.gameroom.creator;

import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Animal;
import by.epam.gameroom.toy.characteristic.EnumHelper;
import by.epam.gameroom.toy.PlushToy;

public class PlushToyCreator extends ToyCreator {
    @Override
    public Toy factoryMethod() {
		Random rand = new Random();
		
		Size size = EnumHelper.randomEnumValue(Size.class);
		Color color = EnumHelper.randomEnumValue(Color.class);
		int cost = rand.nextInt(90_000) + 10_000;
		Animal animal = EnumHelper.randomEnumValue(Animal.class);
		boolean talking = rand.nextBoolean();
		
		return new PlushToy(size, color, cost, animal, talking);
	}
}
