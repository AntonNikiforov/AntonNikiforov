package by.epam.gameroom.toy.characteristic;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public enum Color {
	
	WHITE, RED, GREEN, BLUE, GREY, BLACK, VARICOLORED;
	
	public static Color randomColor()  {
		
		List<Color> values =
			Collections.unmodifiableList(Arrays.asList(values()));
		int size = values.size();
		Random random = new Random();
		
		return values.get(random.nextInt(size));
	}
}
