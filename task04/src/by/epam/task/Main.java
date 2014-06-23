package by.epam.task;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import by.epam.cards.*;

public class Main {
	
	public static Theme randomTheme()  {
		List<Theme> values = Collections.unmodifiableList(Arrays.asList(Theme.values()));
		int size = values.size();
		Random rand = new Random();
		return values.get(rand.nextInt(size));
	}
	
	public static Type randomType()  {
		List<Type> values = Collections.unmodifiableList(Arrays.asList(Type.values()));
		int size = values.size();
		Random rand = new Random();
		return values.get(rand.nextInt(size));
	}
	
	public static Valuable randomValuable()  {
		List<Valuable> values = Collections.unmodifiableList(Arrays.asList(Valuable.values()));
		int size = values.size();
		Random rand = new Random();
		return values.get(rand.nextInt(size));
	}
	
	public static void main(String[] args) {

		List<OldCard> list = new ArrayList<>();
		Random rand = new Random();
		OldCard card;
		int numberOfCards = 3;
		
		while (numberOfCards-- > 0) {
			card = new OldCard();
			
			card.setTheme(randomTheme());
			card.setType(randomType());
			card.setCountry("Country");
			card.setYear(rand.nextInt(100) + 1900);
			if (rand.nextBoolean()) {
				card.setAuthor("Author");
			}
			card.setValuable(randomValuable());
			if (rand.nextBoolean()) {
				card.setSent(rand.nextBoolean());
			}
			list.add(card);
		}
		
		Cards cards = new Cards();
		cards.getOldCard().addAll(list);

		try {

			File file = new File("cards.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Cards.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(cards, file);
			jaxbMarshaller.marshal(cards, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
}
