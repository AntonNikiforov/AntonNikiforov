package by.training.webxml.dao.impl;

import by.training.webxml.dao.XmlDao;
import by.training.webxml.dao.XmlDaoException;
import by.training.webxml.dao.XmlDaoFactory;
import by.training.webxml.entity.OldCard;
import by.training.webxml.entity.Theme;
import by.training.webxml.entity.Type;
import by.training.webxml.entity.Valuable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SaxXmlDao extends DefaultHandler implements XmlDao {

    public static void main(String[] args) throws XmlDaoException {
        XmlDao dao = XmlDaoFactory.getInstance().getXmlDao(XmlDaoFactory.SAX);
        List<OldCard> cards = dao.parse("web/xml/cards.xml");
        for (OldCard card : cards) {
            System.out.println(card);
        }
    }

    private static final XmlDao instance = new SaxXmlDao();

    private SaxXmlDao() {}

    public static XmlDao getInstance() {
        return instance;
    }

    @Override
    public List<OldCard> parse(String filename) throws XmlDaoException {

        List<OldCard> cards;

        try {

            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            SaxXmlDaoHelper helper = new SaxXmlDaoHelper();
            parser.parse(new File(filename), helper);

            cards = helper.getCards();

        } catch (ParserConfigurationException e) {
            throw new XmlDaoException(e.toString(), e);
        } catch (SAXException e) {
            throw new XmlDaoException(e.toString(), e);
        } catch (IOException e) {
            throw new XmlDaoException(e.toString(), e);
        }

        return cards;
    }

    private class SaxXmlDaoHelper extends DefaultHandler {

        private List<OldCard> cards = new LinkedList<>();
        private OldCard card = null;
        private String characters = null;

        private List<OldCard> getCards() {
            return cards;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{

            try {
                XmlTag tag = XmlTag.valueOf(qName.toUpperCase());

                switch (tag) {

                    case OLDCARD:
                        card = new OldCard();
                        break;
                }
            } catch (IllegalArgumentException e) {
                throw new SAXException(e.toString(), e);
            }
        }

        @Override
        public void characters(char[] buffer, int start, int length) throws SAXException{
            characters = new String(buffer, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException{

            try {
                XmlTag tag = XmlTag.valueOf(qName.toUpperCase());

                switch (tag) {

                    case OLDCARD:
                        cards.add(card);
                        break;

                    case THEME:
                        card.setTheme(Theme.valueOf(characters.toUpperCase()));
                        break;

                    case TYPE:
                        card.setType(Type.valueOf(characters.toUpperCase()));
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
                        card.setValuable(Valuable.valueOf(characters.toUpperCase()));
                        break;

                    case SENT:
                        card.setSent(Boolean.parseBoolean(characters));
                        break;
                }
            } catch (IllegalArgumentException e) {
                throw new SAXException(e.toString(), e);
            }
        }
    }
}
