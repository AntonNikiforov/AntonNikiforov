package by.training.library.command;

import by.training.library.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandHelper {

    private static final CommandHelper instance = new CommandHelper();
    private Map<String, Command> commands = new HashMap<String, Command>();

    private CommandHelper() {
        commands.put(Command.START,             new StartPage());
        commands.put(Command.SWITCH_LANGUAGE,   new SwitchLanguage());
        commands.put(Command.SIGN_IN,           new Login());
        commands.put(Command.SIGN_UP,           new AddUser());
        commands.put(Command.SIGN_OUT,          new Logout());
        commands.put(Command.SEARCH,            new Search());
        commands.put(Command.USERS,             new ShowUsers());
        commands.put(Command.USER,              new ShowUser());
        commands.put(Command.ADD_USER,          new AddUser());
        commands.put(Command.EDIT_USER,         new EditUser());
        commands.put(Command.DELETE_USER,       new DeleteUser());
        commands.put(Command.BOOKS,             new ShowBooks());
        commands.put(Command.BOOK,              new ShowBook());
        commands.put(Command.ADD_BOOK,          new AddBook());
        commands.put(Command.EDIT_BOOK,         new EditBook());
        commands.put(Command.DELETE_BOOK,       new DeleteBook());
        commands.put(Command.BOOKINGS,          new ShowBookings());
        commands.put(Command.BOOKING,           new ShowBooking());
        commands.put(Command.ADD_BOOKING,       new AddBooking());
        commands.put(Command.CLOSE_BOOKING,     new CloseBooking());
    }

    public static CommandHelper getInstance() {
        return instance;
    }

    public String getCommandName(HttpServletRequest request) {
        return request.getServletPath();
    }

    public Command getCommand(String commandName) {

        Command command = commands.get(commandName);

        if (null == command) {
            command = NoSuchCommand.getInstance();
        }

        System.out.println(commandName);
        System.out.println(command.getClass().getSimpleName());
        return command;
    }
}
