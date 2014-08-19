package by.training.library.service;

import by.training.library.dao.BookDao;
import by.training.library.dao.DaoException;
import by.training.library.dao.Dao;
import by.training.library.dao.GenreDao;
import by.training.library.entity.Book;
import by.training.library.entity.Booking;
import by.training.library.entity.Genre;
import by.training.library.service.exception.DeleteException;
import by.training.library.service.exception.NoMoreBooksException;
import by.training.library.service.exception.NoSuchBookException;
import by.training.library.service.exception.ServiceException;
import by.training.library.util.SearchHelper;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class BookService {

    private static final BookService instance = new BookService();

    private BookService() {}

    public static BookService getInstance() {
        return instance;
    }

    public Book createBook(String name, String author, int year, int num, int genreId) throws ServiceException {
        if (name == null || author == null) {
            throw new IllegalArgumentException();
        }
        if (year < 0 || year > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new IllegalArgumentException();
        }
        if (num <= 0 || genreId <= 0) {
            throw new IllegalArgumentException();
        }

        try {
            Dao<Book> dao = BookDao.getInstance();
            Dao<Genre> genreDao = GenreDao.getInstance();

            Genre genre = genreDao.read(genreId);

            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setYear(year);
            book.setNum(num);
            book.setGenre(genre);

            return dao.create(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Book readBook(int bookId) throws ServiceException {
        try {
            Dao<Book> dao = BookDao.getInstance();

            Book book = dao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no book with id " + bookId);
            }
            return book;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteBook(int bookId) throws ServiceException {
        try {
            readBook(bookId);

            int numOfOpenBookings = getAllOpenBookings(bookId).size();
            if (numOfOpenBookings > 0) {
                throw new DeleteException("not enough " + numOfOpenBookings + " books");
            }

            BookingService service = BookingService.getInstance();

            for (Booking booking : getAllBookings(bookId)) {
                service.deleteBooking(booking.getId());
            }

            Dao<Book> dao = BookDao.getInstance();

            dao.delete(bookId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeName(int bookId, String name) throws ServiceException {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        try {
            Dao<Book> dao = BookDao.getInstance();
            Book book = dao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no book with id " + bookId);
            }
            book.setName(name);
            dao.update(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeAuthor(int bookId, String author) throws ServiceException {
        if (author == null) {
            throw new IllegalArgumentException();
        }

        try {
            Dao<Book> dao = BookDao.getInstance();
            Book book = dao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no book with id " + bookId);
            }
            book.setAuthor(author);
            dao.update(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeYear(int bookId, int year) throws ServiceException {
        if (year <= 0) {
            throw new IllegalArgumentException();
        }

        try {
            Dao<Book> dao = BookDao.getInstance();
            Book book = dao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no book with id " + bookId);
            }
            book.setYear(year);
            dao.update(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeNum(int bookId, int num) throws ServiceException {
        if (num < 0) {
            throw new IllegalArgumentException();
        }

        try {
            Dao<Book> dao = BookDao.getInstance();
            Book book = dao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no book with id " + bookId);
            }
            int numInLibNow = book.getNum() - getAllOpenBookings(bookId).size();
            if (numInLibNow - num < 0) throw new NoMoreBooksException(numInLibNow + " - " + num + " < 0");

            book.setNum(num);
            dao.update(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeGenre(int bookId, int genreId) throws ServiceException {
        try {
            Dao<Book> dao = BookDao.getInstance();
            Dao<Genre> genreDao = GenreDao.getInstance();

            Genre genre = genreDao.read(genreId);
            if (genre == null) {
                throw new ServiceException("no genre with id " + genreId);
            }
            Book book = dao.read(bookId);
            if (book == null) {
                throw new NoSuchBookException("no book with id " + bookId);
            }
            book.setGenre(genre);
            dao.update(book);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int getNumOfBooks() throws ServiceException {
        try {
            Dao<Book> dao = BookDao.getInstance();

            return dao.getAll().size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> getAll(int page, int numOnPage) throws ServiceException {
        if (page <= 0) throw new IllegalArgumentException();
        if (numOnPage <= 0) throw new IllegalArgumentException();

        int toIndex = page * numOnPage;
        int fromIndex = toIndex - numOnPage;

        try {
            Dao<Book> dao = BookDao.getInstance();

            List<Book> list = dao.getAll();
            int size = list.size();

            if (fromIndex > size) return new LinkedList<Book>();
            if (toIndex > size) toIndex = size;

            return list.subList(fromIndex, toIndex);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> searchByName(String name) throws ServiceException {
        if (name == null) throw new IllegalArgumentException();

        String regexp = SearchHelper.getRegex(name);
        Pattern pattern = Pattern.compile(regexp);

        try {
            Dao<Book> dao = BookDao.getInstance();

            List<Book> list = new LinkedList<Book>();

            for (Book book : dao.getAll()) {

                if (pattern.matcher(book.getName().toLowerCase()).find()) {
                    list.add(book);
                }
            }

            return list;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Book> searchByName(String name, int page, int numOnPage) throws ServiceException {
        if (name == null) throw new IllegalArgumentException();
        if (page <= 0) throw new IllegalArgumentException();
        if (numOnPage <= 0) throw new IllegalArgumentException();

        int toIndex = page * numOnPage;
        int fromIndex = toIndex - numOnPage;

        List<Book> list = searchByName(name);
        int size = list.size();

        if (fromIndex > size) return new LinkedList<Book>();
        if (toIndex > size) toIndex = size;

        return list.subList(fromIndex, toIndex);
    }

    public List<Genre> getAllGenres() throws ServiceException {
        try {
            Dao<Genre> dao = GenreDao.getInstance();
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Booking> getAllBookings(int bookId) throws ServiceException {
        readBook(bookId);
/*
        try {
            Dao<Booking> dao = BookingDao.getInstance();
            List<Booking> list = new LinkedList<Booking>();

            for (Booking booking : dao.getAll()) {
                if (booking.getBook().getId() == bookId) {
                    list.add(booking);
                }
            }

            return list;

        } catch (DaoException e) {
            throw new ServiceException(e.toString(), e);
        }
*/
        BookingService service = BookingService.getInstance();
        //Dao<Booking> dao = BookingDao.getInstance();
        List<Booking> list = new LinkedList<Booking>();

        for (Booking booking : service.getAllBookings()) {
            if (booking.getBook().getId() == bookId) {
                list.add(booking);
            }
        }

        return list;
    }

    public List<Booking> getAllOpenBookings(int bookId) throws ServiceException {
        readBook(bookId);

        List<Booking> list = new LinkedList<Booking>();

        for (Booking booking : getAllBookings(bookId)) {
            if (booking.getDateOfReturn() == null) {
                list.add(booking);
            }
        }

        return list;
    }
}
