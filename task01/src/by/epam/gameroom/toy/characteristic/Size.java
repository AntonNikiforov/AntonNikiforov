package by.epam.gameroom.toy.characteristic;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public enum Size {
	
	SMALL, MIDSIZE, LARGE;
	
	public static Size randomSize()  {
		
		List<Size> values =
			Collections.unmodifiableList(Arrays.asList(values()));
		int size = values.size();
		Random random = new Random();
		
		return values.get(random.nextInt(size));
	}
}
