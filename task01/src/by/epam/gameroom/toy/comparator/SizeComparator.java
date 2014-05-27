package by.epam.gameroom.toy.comparator;

import java.util.Comparator;

import by.epam.gameroom.toy.Toy;

public class SizeComparator implements Comparator<Toy> {
	public int compare(Toy t1, Toy t2) {
		return Integer.compare(t1.getSize().ordinal(),
								t2.getSize().ordinal());
	}
}
