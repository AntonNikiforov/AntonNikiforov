package by.epam.gameroom.toy.characteristic;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public enum Material {
	
	FABRIC, RUBBER, PLASTIC, OTHER;
	
	public static Material randomMaterial()  {
		
		List<Material> values =
			Collections.unmodifiableList(Arrays.asList(values()));
		int size = values.size();
		Random random = new Random();
		
		return values.get(random.nextInt(size));
	}
}
