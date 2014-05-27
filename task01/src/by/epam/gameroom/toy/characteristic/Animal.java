package by.epam.gameroom.toy.characteristic;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public enum Animal {
	
	CAT, DOG, FOX, HORSE, COW, BEAR, OTHER;
	
	public static Animal randomAnimal()  {
		
		List<Animal> values =
			Collections.unmodifiableList(Arrays.asList(values()));
		int size = values.size();
		Random random = new Random();
		
		return values.get(random.nextInt(size));
	}
}
