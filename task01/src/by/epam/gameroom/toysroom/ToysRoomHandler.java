package by.epam.gameroom.toysroom;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import by.epam.gameroom.toy.Toy;
import by.epam.gameroom.toy.Ball;
import by.epam.gameroom.toy.Car;
import by.epam.gameroom.toy.PlushToy;
import by.epam.gameroom.toy.comparator.CostComparator;
import by.epam.gameroom.toy.characteristic.Size;
import by.epam.gameroom.toy.characteristic.Color;
import by.epam.gameroom.toy.characteristic.Material;
import by.epam.gameroom.toy.characteristic.CarType;
import by.epam.gameroom.toy.characteristic.Animal;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// read
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamConstants;
// write
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamException;

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
	
	public static void toXML(ToysRoom room, String file) {
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();

		try {
			
			XMLStreamWriter writer = factory.createXMLStreamWriter(new FileWriter(file));

			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeCharacters("\n");
			writer.writeStartElement("toysroom");
			
			for (Toy toy : room.getToysList()) {
				
				String className = toy.getClass().getSimpleName();
				
				// class name
				writer.writeCharacters("\n\t");
				writer.writeStartElement(className.toLowerCase());
				
				// toy size
				writer.writeCharacters("\n\t\t");
				writer.writeStartElement("size");
				writer.writeCharacters(String.valueOf(toy.getSize()));
				writer.writeEndElement();
				
				// color
				writer.writeCharacters("\n\t\t");
				writer.writeStartElement("color");
				writer.writeCharacters(String.valueOf(toy.getColor()));
				writer.writeEndElement();
				
				// material
				writer.writeCharacters("\n\t\t");
				writer.writeStartElement("material");
				writer.writeCharacters(String.valueOf(toy.getMaterial()));
				writer.writeEndElement();
				
				// cost
				writer.writeCharacters("\n\t\t");
				writer.writeStartElement("cost");
				writer.writeCharacters(String.valueOf(toy.getCost()));
				writer.writeEndElement();
				
				// ball fields
				if (className.equalsIgnoreCase("Ball")) {
					writer.writeCharacters("\n\t\t");
					writer.writeStartElement("diametr");
					writer.writeCharacters(String.valueOf( ((Ball)toy).getDiametr() ));
					writer.writeEndElement();
					
					writer.writeCharacters("\n\t\t");
					writer.writeStartElement("pressure");
					writer.writeCharacters(String.valueOf( ((Ball)toy).getPressure() ));
					writer.writeEndElement();
				}
				
				if (className.equalsIgnoreCase("Car")) {
					
					writer.writeCharacters("\n\t\t");
					writer.writeStartElement("cartype");
					writer.writeCharacters(String.valueOf( ((Car)toy).getCarType() ));
					writer.writeEndElement();
				}
				
				if (className.equalsIgnoreCase("PlushToy")) {
					writer.writeCharacters("\n\t\t");
					writer.writeStartElement("animal");
					writer.writeCharacters(String.valueOf( ((PlushToy)toy).getAnimal() ));
					writer.writeEndElement();
					
					writer.writeCharacters("\n\t\t");
					writer.writeStartElement("talking");
					writer.writeCharacters(String.valueOf( ((PlushToy)toy).isTalking() ));
					writer.writeEndElement();
				}
				
				writer.writeCharacters("\n\t");
				writer.writeEndElement();
			}
			
			// </toysroom>
			writer.writeCharacters("\n");
			writer.writeEndElement();
			writer.writeEndDocument();

			writer.flush();
			writer.close();

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ToysRoom fromXML(String file) {
		
		ToysRoom room = new ToysRoom();
		Toy toy = null;
		String text = null;
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		try {
			
			XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
			
			label: while (reader.hasNext()) {
				int event = reader.next();
				
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						
						switch (reader.getLocalName()) {
							case "ball":
								toy = new Ball();
							break;
								
							case "car":
								toy = new Car();
							break;
							
							case "plushtoy":
								toy = new PlushToy();
							break;
						}
					break;
					
					case XMLStreamConstants.CHARACTERS:
					
                         text = reader.getText().trim();
					break;
                    
                    case XMLStreamConstants.END_ELEMENT:
						
						switch (reader.getLocalName()) {
							
							case "ball":
							
							case "car":
							
							case "plushtoy":
								room.addToy(toy);
							break;
							
							case "size":
								toy.setSize(Size.valueOf(text));
							break;
								
							case "color":
								toy.setColor(Color.valueOf(text));
							break;
								
							case "material":
								toy.setMaterial(Material.valueOf(text));
							break;
								
							case "cost":
								toy.setCost(Integer.parseInt(text));
							break;
							
							case "diametr":
								((Ball)toy).setDiametr(Float.parseFloat(text));
							break;
								
							case "pressure":
								((Ball)toy).setPressure(Float.parseFloat(text));
							break;
								
							case "cartype":
								((Car)toy).setCarType(CarType.valueOf(text));
							break;
								
							case "animal":
								((PlushToy)toy).setAnimal(Animal.valueOf(text));
							break;
								
							case "talking":
								((PlushToy)toy).setTalking(Boolean.parseBoolean(text));
							break;
						}
					break;
				}
			}
			
			reader.close();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return room;
	}
	
	public static ToysRoom fillToysRoomFromXML(String file, int balance) {
		if (balance < 0) {
			throw new IllegalArgumentException();
		}
		
		ToysRoom room = new ToysRoom();
		Toy toy = null;
		String text = null;
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		try {
			
			XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
			
			
			label:	// if balance < 0 break label
			while (reader.hasNext()) {
				int event = reader.next();
				
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						
						switch (reader.getLocalName()) {
							case "ball":
								toy = new Ball();
							break;
								
							case "car":
								toy = new Car();
							break;
							
							case "plushtoy":
								toy = new PlushToy();
							break;
						}
					break;
					
					case XMLStreamConstants.CHARACTERS:
					
                         text = reader.getText().trim();
					break;
                    
                    case XMLStreamConstants.END_ELEMENT:
						
						switch (reader.getLocalName()) {
							
							case "ball":
							
							case "car":
							
							case "plushtoy":
								// it is necessary to check to not go into debt
								balance -= toy.getCost();
								if (balance < 0) {
									break label;
								}
								room.addToy(toy);
							break;
							
							case "size":
								toy.setSize(Size.valueOf(text));
							break;
								
							case "color":
								toy.setColor(Color.valueOf(text));
							break;
								
							case "material":
								toy.setMaterial(Material.valueOf(text));
							break;
								
							case "cost":
								toy.setCost(Integer.parseInt(text));
							break;
							
							case "diametr":
								((Ball)toy).setDiametr(Float.parseFloat(text));
							break;
								
							case "pressure":
								((Ball)toy).setPressure(Float.parseFloat(text));
							break;
								
							case "cartype":
								((Car)toy).setCarType(CarType.valueOf(text));
							break;
								
							case "animal":
								((PlushToy)toy).setAnimal(Animal.valueOf(text));
							break;
								
							case "talking":
								((PlushToy)toy).setTalking(Boolean.parseBoolean(text));
							break;
						}
					break;
				}
			}
			
			reader.close();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return room;
	}
}
