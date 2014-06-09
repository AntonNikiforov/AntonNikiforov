package by.epam.gameroom.toysroom;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.comparator.CostComparator;
import by.epam.gameroom.creator.ToyCreator;
import by.epam.gameroom.creator.CarCreator;
import by.epam.gameroom.creator.BallCreator;
import by.epam.gameroom.creator.PlushToyCreator;

public class ToysRoomHandler {
	public static List<Toy> searchByRangeOfCost(ToysRoom room,
												int minCost,
												int maxCost) {
		List<Toy> toys = new ArrayList<>();
		if (maxCost - minCost >= 0) {
			for (Toy toy : room.getToysList()) {
				if (minCost <= toy.getCost() && toy.getCost() <= maxCost) {
					toys.add(toy);
				}
			}
		}
		return toys;
	}
	
	public static int getNumberOfToys(ToysRoom room) {
		return room.getToysList().size();
	}
	
	public static void sortToysByCost(ToysRoom room) {
		Collections.sort(room.getToysList(), new CostComparator());
	}
}
