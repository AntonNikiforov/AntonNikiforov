package by.epam.gameroom.creator;

import java.util.Random;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.EnumHelper;
import by.epam.gameroom.toy.Ball;

public class BallCreator extends ToyCreator {
    @Override
    public Toy factoryMethod() {
		Random rand = new Random();
		
		Color color = EnumHelper.randomEnumValue(Color.class);
		int cost = rand.nextInt(90_000) + 10_000;
		float diametr = rand.nextFloat() * 50;
		float pressure = rand.nextFloat() * 20;
		
		return new Ball(color, cost, diametr, pressure);
	}
}
