package by.training.webxml.dao.impl;

import by.training.webxml.dao.XmlDao;
import by.training.webxml.dao.XmlDaoException;
import by.training.webxml.dao.XmlDaoFactory;
import by.training.webxml.entity.OldCard;
import by.training.webxml.entity.Theme;
import by.training.webxml.entity.Type;
import by.training.webxml.entity.Valuable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class StaxXmlDao implements XmlDao {

    public static void main(String[] args) throws XmlDaoException {
        XmlDao dao = XmlDaoFactory.getInstance().getXmlDao(XmlDaoFactory.STAX);
        List<OldCard> cards = dao.parse("web/xml/cards.xml");
        for (OldCard card : cards) {
            System.out.println(card);
        }
    }

    private static final XmlDao instance = new StaxXmlDao();

    private StaxXmlDao() {}

    public static XmlDao getInstance() {
        return instance;
    }

    @Override
    public List<OldCard> parse(String filename) throws XmlDaoException {
        List<OldCard> cards = new LinkedList<>();
        OldCard card = null;
        String characters = null;
        XmlTag tag;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(filename));

            while (reader.hasNext()) {
                switch (reader.next()) {

                    case XMLStreamReader.START_ELEMENT:

                        try {
                            tag = XmlTag.valueOf(reader.getLocalName().toUpperCase());

                            switch (tag) {

                                case OLDCARD:
                                    card = new OldCard();
                                    break;
                            }
                        } catch (IllegalArgumentException e) {
                            throw new XmlDaoException(e.toString(), e);
                        }
                        break;

                    case XMLStreamReader.CHARACTERS:
                        characters = reader.getText().trim();
                        break;

                    case XMLStreamReader.END_ELEMENT:
                        try {
                            tag = XmlTag.valueOf(reader.getLocalName().toUpperCase());

                            switch (tag) {

                                case OLDCARD:
                                    cards.add(card);
                                    break;

                                case THEME:
                                    card.setTheme(Theme.valueOf(characters));
                                    break;

                                case TYPE:
                                    card.setType(Type.valueOf(characters));
                                    break;

                                case COUNTRY:
                                    card.setCountry(characters);
                                    break;

                                case YEAR:
                                    card.setYear(Integer.parseInt(characters));
                                    break;

                                case AUTHOR:
                                    card.setAuthor(characters);
                                    break;

                                case VALUABLE:
                                    card.setValuable(Valuable.valueOf(characters));
                                    break;

                                case SENT:
                                    card.setSent(Boolean.parseBoolean(characters));
                                    break;
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            throw new XmlDaoException(e.toString(), e);
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new XmlDaoException(e.toString(), e);
        } catch (XMLStreamException e) {
            throw new XmlDaoException(e.toString(), e);
        }

        return cards;
    }
}
