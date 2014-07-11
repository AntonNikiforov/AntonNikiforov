package by.training.webxml.dao;

import by.training.webxml.entity.OldCard;

import java.util.List;

public interface XmlDao {

    public List<OldCard> parse(String filename) throws XmlDaoException;
}
