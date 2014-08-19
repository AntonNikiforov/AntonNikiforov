package by.training.library.service;

import by.training.library.dao.DaoException;
import by.training.library.dao.Dao;
import by.training.library.dao.LangDao;
import by.training.library.dao.RoleDao;
import by.training.library.dao.UserDao;
import by.training.library.entity.Booking;
import by.training.library.entity.Lang;
import by.training.library.entity.Role;
import by.training.library.entity.User;
import by.training.library.service.exception.DeleteException;
import by.training.library.service.exception.NoSuchUserException;
import by.training.library.service.exception.ServiceException;
import by.training.library.service.exception.UserAlreadyExistsException;
import by.training.library.util.Security;

import java.util.LinkedList;
import java.util.List;

public class UserService {

    private static final UserService instance = new UserService();

    private UserService() {}

    public static UserService getInstance() {
        return instance;
    }

    public User login(String email, String password) throws ServiceException {
        if (email == null || password == null) {
            throw new ServiceException("illegal argument");
        }

        User user = null;
        password = Security.getHashCode(password);

        try {
            Dao<User> dao = UserDao.getInstance();

            for (User aUser : dao.getAll()) {
                if (aUser.getEmail().equals(email) && aUser.getPassword().equals(password)) {
                    user = aUser;
                    break;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        if (user != null) {
            user.setEmail(Security.hideEmail(user.getEmail()));
            user.setPassword(Security.hidePassword());
        }
        return user;
    }

    public User createUser(String email, String password, String name, String surname) throws ServiceException {
        if (email == null || password == null || name == null || surname == null) {
            throw new IllegalArgumentException();
        }

        if (login(email, password) != null) {
            System.out.println(email);
            throw new UserAlreadyExistsException("user with email: " + email + " already exists");
        }

        try {
            Dao<Lang> langDao = LangDao.getInstance();
            Lang lang = langDao.read(1);

            Dao<Role> roleDao = RoleDao.getInstance();
            Role role = roleDao.read(2);

            User user = new User();
            user.setEmail(email);
            user.setPassword(Security.getHashCode(password));
            user.setName(name);
            user.setSurname(surname);
            user.setLang(lang);
            user.setRole(role);

            Dao<User> dao = UserDao.getInstance();
            user = dao.create(user);

            if (user != null) {
                user.setEmail(Security.hideEmail(user.getEmail()));
                user.setPassword(Security.hidePassword());
            }

            return user;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User readUser(int id) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();
            User user = dao.read(id);

            if (user == null) {
                throw new NoSuchUserException("no user with id :" + id);
            }

            user.setEmail(Security.hideEmail(user.getEmail()));
            user.setPassword(Security.hidePassword());

            return user;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteUser(int id) throws ServiceException {
        try {
            readUser(id);

            int numOfOpenBookings = getAllOpenBookings(id).size();
            if (numOfOpenBookings > 0) {
                throw new DeleteException("user have " + numOfOpenBookings + " open bookings");
            }

            BookingService service = BookingService.getInstance();

            for (Booking booking : getAllBookings(id)) {
                service.deleteBooking(booking.getId());
            }

            Dao<User> dao = UserDao.getInstance();
            dao.delete(id);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeEmail(int id, String email) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();

            User user = dao.read(id);
            if (user == null) {
                throw new NoSuchUserException("no user with id " + id);
            }
            user.setEmail(email);
            dao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changePassword(int id, String password) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();

            User user = dao.read(id);
            if (user == null) {
                throw new NoSuchUserException("no user with id " + id);
            }
            user.setPassword(Security.getHashCode(password));
            dao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeName(int id, String name) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();

            User user = dao.read(id);
            if (user == null) {
                throw new NoSuchUserException("no user with id " + id);
            }
            user.setName(name);
            dao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeSurname(int id, String surname) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();

            User user = dao.read(id);
            if (user == null) {
                throw new NoSuchUserException("no user with id " + id);
            }
            user.setSurname(surname);
            dao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeLanguage(int id, int langId) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();
            Dao<Lang> langDao = LangDao.getInstance();

            Lang lang = langDao.read(langId);
            if (lang == null) {
                throw new ServiceException("no lang with id " + langId);
            }
            User user = dao.read(id);
            if (user == null) {
                throw new NoSuchUserException("no user with id " + id);
            }
            user.setLang(lang);
            dao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeRole(int id, int roleId) throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();
            Dao<Role> roleDao = RoleDao.getInstance();

            Role role = roleDao.read(roleId);
            if (role == null) {
                throw new ServiceException("no role with id " + roleId);
            }
            User user = dao.read(id);
            if (user == null) {
                throw new NoSuchUserException("no user with id " + id);
            }
            user.setRole(role);
            dao.update(user);

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int getNumOfUsers() throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();
            return dao.getAll().size();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> getAllUsers() throws ServiceException {
        try {
            Dao<User> dao = UserDao.getInstance();

            List<User> list = dao.getAll();

            for (User user : list) {
                user.setEmail(Security.hideEmail(user.getEmail()));
                user.setPassword(Security.hidePassword());
            }
            return list;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> getAllUsers(int page, int numOnPage) throws ServiceException {
        if (page <= 0) throw new IllegalArgumentException();
        if (numOnPage <= 0) throw new IllegalArgumentException();

        int toIndex = page * numOnPage;
        int fromIndex = toIndex - numOnPage;

        List<User> list = getAllUsers();
        int size = list.size();

        if (fromIndex > size) return new LinkedList<User>();
        if (toIndex > size) toIndex = size;

        return list.subList(fromIndex, toIndex);
    }

    public List<Role> getAllRoles() throws ServiceException {
        try {
            Dao<Role> dao = RoleDao.getInstance();
            return dao.getAll();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Role readRole(int roleId) throws ServiceException {
        try {
            Dao<Role> dao = RoleDao.getInstance();
            Role role = dao.read(roleId);
            if (role == null) {
                throw new ServiceException("no role with id " + roleId);
            }
            return role;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Lang> getAllLangs() throws ServiceException {
        try {
            Dao<Lang> dao = LangDao.getInstance();
            return dao.getAll();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Lang readLang(int langId) throws ServiceException {
        try {
            Dao<Lang> dao = LangDao.getInstance();
            Lang lang = dao.read(langId);
            if (lang == null) {
                throw new ServiceException("no lang with id " + langId);
            }
            return lang;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Booking> getAllBookings(int userId) throws ServiceException {
        readUser(userId);
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
            if (booking.getUser().getId() == userId) {
                list.add(booking);
            }
        }

        return list;
    }

    public List<Booking> getAllOpenBookings(int userId) throws ServiceException {
        readUser(userId);

        List<Booking> list = new LinkedList<Booking>();

        for (Booking booking : getAllBookings(userId)) {
            if (booking.getDateOfReturn() == null) {
                list.add(booking);
            }
        }

        return list;
    }
}
