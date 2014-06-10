package by.epam.gameroom.main;

import java.util.List;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toysroom.ToysRoom;
import by.epam.gameroom.toysroom.ToysRoomHandler;
import by.epam.gameroom.toysroom.AgeCategory;
//import by.epam.gameroom.creator.ToysRoomCreator;

public class Main {
	public static void main(String[] args) {
		/*
		ToysRoom r;
		r = ToysRoomCreator.createRoomByBalance(AgeCategory.CHILD, 2_000_000);
		ToysRoomHandler.toXML(r, "room.xml");
		*/
		ToysRoom room;
		room = ToysRoomHandler.fillToysRoomFromXML("room.xml", 1_000_000);
		ToysRoomHandler.sortToysByCost(room);
		
		for (Toy toy : room.getToysList()) {
			System.out.println(toy);
		}
		System.out.println(ToysRoomHandler.getNumberOfToys(room));
		System.out.println("-----------");
		
		List<Toy> toys =
			ToysRoomHandler.searchByRangeOfCost(room, 50_000, 60_000);
			
		for (Toy toy : toys) {
			System.out.println(toy);
		}
		System.out.println(toys.size());

	}
}
