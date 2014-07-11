package by.training.webxml.dao.impl;

import by.training.webxml.dao.XmlDao;
import by.training.webxml.dao.XmlDaoException;
import by.training.webxml.dao.XmlDaoFactory;
import by.training.webxml.entity.OldCard;
import by.training.webxml.entity.Theme;
import by.training.webxml.entity.Type;
import by.training.webxml.entity.Valuable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DomXmlDao implements XmlDao {

    public static void main(String[] args) throws XmlDaoException {
        XmlDao dao = XmlDaoFactory.getInstance().getXmlDao(XmlDaoFactory.DOM);
        List<OldCard> cards = dao.parse("web/xml/cards.xml");
        for (OldCard card : cards) {
            System.out.println(card);
        }
    }

    private static final XmlDao instance = new DomXmlDao();

    private DomXmlDao() {}

    public static XmlDao getInstance() {
        return instance;
    }

    @Override
    public List<OldCard> parse(String filename) throws XmlDaoException {
        List<OldCard> cards = new LinkedList<>();
        OldCard card;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filename));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(XmlTag.CARDS.toString().toLowerCase());
            //System.out.println(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {

                card = new OldCard();
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    card.setTheme(Theme.valueOf(element.getElementsByTagName(XmlTag.THEME.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                    card.setType(Type.valueOf(element.getElementsByTagName(XmlTag.TYPE.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                    card.setCountry(String.valueOf(element.getElementsByTagName(XmlTag.COUNTRY.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                    card.setYear(Integer.parseInt(element.getElementsByTagName(XmlTag.YEAR.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                    card.setAuthor(String.valueOf(element.getElementsByTagName(XmlTag.AUTHOR.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                    card.setValuable(Valuable.valueOf(element.getElementsByTagName(XmlTag.VALUABLE.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                    card.setSent(Boolean.parseBoolean(element.getElementsByTagName(XmlTag.SENT.toString().toLowerCase()).item(0).getTextContent().toUpperCase()));
                }
                cards.add(card);
            }

        } catch (ParserConfigurationException e) {
            throw new XmlDaoException(e.toString(), e);
        } catch (SAXException e) {
            throw new XmlDaoException(e.toString(), e);
        } catch (IOException e) {
            throw new XmlDaoException(e.toString(), e);
        } catch (IllegalArgumentException e) {
            throw new XmlDaoException(e.toString(), e);
        }

        return cards;
    }
}
