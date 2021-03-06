package by.epam.gameroom.creator;

import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.CarType;
import by.epam.gameroom.toy.characteristic.EnumHelper;
import by.epam.gameroom.toy.Car;

public class CarCreator extends ToyCreator {
    @Override
    public Toy factoryMethod() {
		Random rand = new Random();
		
		Size size = EnumHelper.randomEnumValue(Size.class);
		Color color = EnumHelper.randomEnumValue(Color.class);
		int cost = rand.nextInt(90_000) + 10_000;
		CarType type = EnumHelper.randomEnumValue(CarType.class);
		
		return new Car(size, color, cost, type);
	}
}
