package by.training.library.tag;

import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Pagination extends TagSupport {

    public static final String QUERY = "q";
    public static final String TYPE = "type";
    public static final String PAGE = "page";

    private Integer numAtAll;

    public Integer getNumAtAll() {
        return numAtAll;
    }

    public void setNumAtAll(Integer numAtAll) {
        this.numAtAll = numAtAll;
    }

    @Override
    public int doStartTag() throws JspException {
        if (numAtAll < 0) {
            throw new JspException("smth wrong");
        }

        // exit
        if (numAtAll == 0) {
            return SKIP_BODY;
        }

        Integer page = (Integer) pageContext.getRequest().getAttribute(PAGE);
        String type = (String) pageContext.getRequest().getAttribute(TYPE);
        String query = (String) pageContext.getRequest().getAttribute(QUERY);

        if (page == null || page <= 0) page = 1;

        int num = ((numAtAll - 1) / (10)) + 1;

        if (num > 1) {
            String pageName = "search?q=" + query + "&type=" + type;

            try {
                JspWriter out = pageContext.getOut();

                out.write("<ul class=\"pagination\">");
                for (int i = 1; i <= num; ++i) {
                    if (page == i) {
                        out.write("<li class=\"active\"><span>" + i + " <span class=\"sr-only\">(current)</span></span></li>");
                    } else {
                        out.write("<li><a href=\"" + pageName + "&page=" + i + "\">" + i + "</a></li>");
                    }
                }
                out.write("</ul>");

            } catch (IOException e) {
                throw new JspException(e.getMessage(), e);
            }
        }
        return SKIP_BODY;
    }
}
