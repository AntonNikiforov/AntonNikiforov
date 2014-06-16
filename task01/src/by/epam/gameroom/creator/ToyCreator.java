package by.epam.gameroom.creator;

import by.epam.gameroom.toy.Toy;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.EnumSet;
import java.util.Random;

public abstract class ToyCreator {
	public abstract Toy factoryMethod();
	
	public static <E extends Enum<E>> E randomEnumValue(Class<E> elementType)  {
		
		Set<E> set = EnumSet.allOf(elementType);
		List<E> values = new ArrayList<>(Collections.unmodifiableSet(set));
		int size = values.size();
		Random random = new Random();
		
		return values.get(random.nextInt(size));
	}
}
