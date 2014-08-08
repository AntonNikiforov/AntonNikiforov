package by.training.library.tag;


import by.training.library.entity.Book;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class Books extends TagSupport {

    private List<Book> books;

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {
            out.write("<div class=\"table-responsive\">\n" +
                    "            <table class=\"table table-hover\">\n" +
                    "                <tr>\n" +
                    "                    <th>name</th>\n" +
                    "                    <th>author</th>\n" +
                    "                    <th>year</th>\n" +
                    "                </tr>");
            for (Book book : books) {
                out.write("<tr>\n" +
                        "      <td><a href=\"book?id=" + book.getId() + "\">" +
                        book.getName() + "</a></td>\n" +
                        "      <td>" + book.getAuthor() + "</td>\n" +
                        "      <td>" + book.getYear() + "</td>\n" +
                        "</tr>");
            }
            out.write("</table>\n" +
                    "        </div>");
        } catch (IOException e) {
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }
}
