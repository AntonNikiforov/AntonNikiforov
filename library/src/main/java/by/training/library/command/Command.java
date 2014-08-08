package by.training.library.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String PREFIX   = "/";

    String NO_SUCH_COMMAND  = "";
    String START            = PREFIX + "start";
    String SEARCH           = PREFIX + "search";
    String SWITCH_LANGUAGE  = PREFIX + "switch_lang";

    // user
    String SIGN_IN          = PREFIX + "sign_in";
    String SIGN_UP          = PREFIX + "sign_up";
    String SIGN_OUT         = PREFIX + "sign_out";

    String USERS            = PREFIX + "users";
    String USER             = PREFIX + "user";
    String ADD_USER         = PREFIX + "add_user";
    String DELETE_USER      = PREFIX + "delete_user";
    String EDIT_USER        = PREFIX + "edit_user";

    // book
    String BOOKS            = PREFIX + "books";
    String BOOK             = PREFIX + "book";
    String ADD_BOOK         = PREFIX + "add_book";
    String DELETE_BOOK      = PREFIX + "delete_book";
    String EDIT_BOOK        = PREFIX + "edit_book";

    //
    String BOOKINGS         = PREFIX + "bookings";
    String BOOKING          = PREFIX + "booking";
    String ADD_BOOKING      = PREFIX + "add_booking";
    String CLOSE_BOOKING    = PREFIX + "close_booking";
    String DELETE_BOOKING   = PREFIX + "delete_booking";
    String EDIT_BOOKING     = PREFIX + "edit_booking";

    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
