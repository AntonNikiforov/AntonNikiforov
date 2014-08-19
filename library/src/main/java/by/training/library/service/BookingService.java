package by.training.library.service;

import by.training.library.dao.DaoException;
import by.training.library.dao.*;
import by.training.library.entity.Book;
import by.training.library.entity.Booking;
import by.training.library.entity.BookingType;
import by.training.library.entity.User;
import by.training.library.service.exception.*;
import by.training.library.util.DateHelper;
import by.training.library.util.Security;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class BookingService {

    private static final BookingService instance = new BookingService();

    private BookingService() {}

    public static BookingService getInstance() {
        return instance;
    }

    public Booking createBooking(int userId, int bookId, int typeId) throws ServiceException {
        try {
            UserDao userDao = UserDao.getInstance();
            User user = userDao.read(userId);

            if (user == null) {
                //throw new ServiceException("no such user");
                throw new NoSuchUserException("no such user");
            }

            BookDao bookDao = BookDao.getInstance();
            Book book = bookDao.read(bookId);

            if (book == null) {
                throw new NoSuchBookException("no such book");
            }

            BookingTypeDao typeDao = BookingTypeDao.getInstance();
            BookingType type = typeDao.read(typeId);

            if (type == null) {
                throw new ServiceException("no such type");
            }

            BookService bookService = BookService.getInstance();

            int numInLibAll = book.getNum();
            int numOfOpenBookings = bookService.getAllOpenBookings(bookId).size();

            int numInLibNow = numInLibAll - numOfOpenBookings;

            if (numInLibNow <= 0) {
                //throw new ServiceException("no more books in library");
                throw new NoMoreBooksException("no more books in library");
            }

            Booking booking = new Booking();

            booking.setUser(user);
            booking.setBook(book);
            booking.setDateOfIssue(DateHelper.getCurrentDate());
            booking.setType(type);

            BookingDao dao = BookingDao.getInstance();

            return dao.create(booking);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Booking readBooking(int id) throws ServiceException {
        try {
            BookingDao dao = BookingDao.getInstance();
            Booking booking = dao.read(id);
            if (booking == null) {
                throw new NoSuchBookingException("no such booking");
            }
            return booking;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteBooking(int id) throws ServiceException {
        readBooking(id);

        try {
            BookingDao dao = BookingDao.getInstance();
            dao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeUser(int id, int userId) throws ServiceException {
        try {
            Dao<User> userDao = UserDao.getInstance();
            Dao<Booking> dao = BookingDao.getInstance();

            User user = userDao.read(userId);
            if (user == null) {
                throw new NoSuchUserException("no such user");
            }
            Booking booking = dao.read(id);
            if (booking == null) {
                throw new NoSuchBookingException("no such booking");
            }
            booking.setUser(user);
            dao.update(booking);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeBook(int id, int bookId) throws ServiceException {
        try {
            Dao<Book> bookDao = BookDao.getInstance();
            Dao<Booking> dao = BookingDao.getInstance();

            Book book = bookDao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no such book");
            }
            Booking booking = dao.read(id);
            if (booking == null) {
                throw new NoSuchBookingException("no such booking");
            }
            booking.setBook(book);
            dao.update(booking);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeDateOfIssue(int id, Date dateOfIssue) throws ServiceException {
        try {
            Dao<Booking> dao = BookingDao.getInstance();

            Booking booking = dao.read(id);
            if (booking == null) {
                throw new NoSuchBookingException("no such booking");
            }
            booking.setDateOfIssue(dateOfIssue);
            dao.update(booking);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeDateOfReturn(int id, Date dateOfReturn) throws ServiceException {
        try {
            Dao<Booking> dao = BookingDao.getInstance();

            Booking booking = dao.read(id);
            if (booking == null) {
                throw new NoSuchBookingException("no such booking");
            }
            booking.setDateOfReturn(dateOfReturn);
            dao.update(booking);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeType(int id, int typeId) throws ServiceException {
        try {
            Dao<BookingType> typeDao = BookingTypeDao.getInstance();
            Dao<Booking> dao = BookingDao.getInstance();

            BookingType type = typeDao.read(typeId);
            if (type == null) {
                throw new ServiceException("no such type");
            }
            Booking booking = dao.read(id);
            if (booking == null) {
                throw new NoSuchBookingException("no such booking");
            }
            booking.setType(type);
            dao.update(booking);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void close(int id) throws ServiceException {
        changeDateOfReturn(id, DateHelper.getCurrentDate());
    }

    public int getNumOfBooking() throws ServiceException {
        try {
            Dao<Booking> dao = BookingDao.getInstance();
            return dao.getAll().size();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Booking> getAllBookings() throws ServiceException {

        try {
            Dao<Booking> dao = BookingDao.getInstance();

            List<Booking> list = dao.getAll();

            for (Booking booking : list) {
                User user = booking.getUser();
                user.setEmail(Security.hideEmail(user.getEmail()));
                user.setPassword(Security.hidePassword());
            }
            return list;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Booking> getAllBookings(int page, int numOnPage) throws ServiceException {
        if (page <= 0) throw new IllegalArgumentException();
        if (numOnPage <= 0) throw new IllegalArgumentException();

        int toIndex = page * numOnPage;
        int fromIndex = toIndex - numOnPage;

        List<Booking> list = getAllBookings();
        int size = list.size();

        if (fromIndex > size) return new LinkedList<Booking>();
        if (toIndex > size) toIndex = size;

        return list.subList(fromIndex, toIndex);
    }

    public List<BookingType> getAllTypes() throws ServiceException {

        try {
            BookingTypeDao dao = BookingTypeDao.getInstance();

            return dao.getAll();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
