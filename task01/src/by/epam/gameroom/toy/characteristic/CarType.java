package by.epam.gameroom.toy.characteristic;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public enum CarType {
	
	PASSANGER, SPORT, LIMOUSINE, BUS, OTHER;
	
	public static CarType randomCarType()  {
		
		List<CarType> values =
			Collections.unmodifiableList(Arrays.asList(values()));
		int size = values.size();
		Random random = new Random();
		
		return values.get(random.nextInt(size));
	}
}
