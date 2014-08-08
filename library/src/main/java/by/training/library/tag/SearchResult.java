package by.training.library.tag;

import by.training.library.entity.Book;
import by.training.library.entity.Booking;
import by.training.library.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class SearchResult extends TagSupport {

    private List var;
    public static final String QUERY = "q";
    public static final String TYPE = "type";

    public List getVar() {
        return var;
    }

    public void setVar(List var) {
        this.var = var;
    }

    @Override
    public int doStartTag() throws JspException {
        if (var == null || var.isEmpty())
        return SKIP_BODY;

        String query = pageContext.getRequest().getParameter(QUERY);
        String type = pageContext.getRequest().getParameter(TYPE);

        String body = "<h1>lalala</h1>";

        if (type == null) type = "book";

        if (type.equals("user")) {
            body = getUsers(var);
        }
        if (type.equals("book")) {
            body = getBooks(var);
        }
        if (type.equals("booking")) {
            body = getBookings(var);
        }

        try {
            pageContext.getOut().write(body);
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }

        return SKIP_BODY;
    }

    private String getUsers(List<User> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"list-group\">");

        for (User user : list) {
            sb.append("<a href=\"user?id=").append(user.getId()).append("\" class=\"list-group-item\">")
                    .append("<h4 class=\"list-group-item-heading\">")
                    .append(user.getName()).append(" ").append(user.getSurname())
                    .append("</h4>")
                    .append("</a>");
        }

        sb.append("</div>");

        return sb.toString();
    }

    private String getBooks(List<Book> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"list-group\">");

        for (Book book : list) {
            sb.append("<a href=\"book?id=").append(book.getId()).append("\" class=\"list-group-item\">")
                    .append("<h4 class=\"list-group-item-heading\">")
                    .append(book.getName()).append(" <small>").append(book.getAuthor()).append("</small>")
                    .append("</h4>")
                    .append("</a>");
        }

        sb.append("</div>");

        return sb.toString();
    }

    private String getBookings(List<Booking> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"list-group\">");

        for (Booking booking : list) {
            sb.append("<a href=\"booking?id=").append(booking.getId()).append("\" class=\"list-group-item\">")
                    .append("<h4 class=\"list-group-item-heading\">")
                    .append(booking.getUser().getName()).append(" <small>").append(booking.getUser().getId()).append("</small>")
                    .append("</h4>")
                    .append("<h4 class=\"list-group-item-heading\">")
                    .append(booking.getBook().getName()).append(" <small>").append(booking.getBook().getId()).append("</small>")
                    .append("</h4>")
                    .append("</a>");
        }

        sb.append("</div>");

        return sb.toString();
    }
}
