package by.training.webxml.dao;

import by.training.webxml.dao.impl.DomXmlDao;
import by.training.webxml.dao.impl.SaxXmlDao;
import by.training.webxml.dao.impl.StaxXmlDao;

public class XmlDaoFactory {

    public static final String DOM = "dom";
    public static final String SAX = "sax";
    public static final String STAX = "stax";

    private final static XmlDaoFactory instance = new XmlDaoFactory();

    public static XmlDaoFactory getInstance() {
        return instance;
    }

    public XmlDao getXmlDao(String xmlDaoType) throws XmlDaoException {
        switch (xmlDaoType) {

            case DOM:
                return DomXmlDao.getInstance();

            case SAX:
                return SaxXmlDao.getInstance();

            case STAX:
                return StaxXmlDao.getInstance();

            default:
                throw new XmlDaoException("no such xml dao");
        }
    }
}
