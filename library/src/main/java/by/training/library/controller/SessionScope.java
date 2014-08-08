package by.training.library.controller;

import javax.servlet.http.HttpServletRequest;

public class SessionScope {

    public static final String USER_ID = "id";
    public static final String ADMIN = "admin";
    public static final String LOCALE = "locale";
    public static final String COMMAND = "command";
    public static final String PAGE = "page";

    public static void setUserId(HttpServletRequest request, int id) {
        request.getSession().setAttribute(USER_ID, id);
    }

    public static Integer getUserId(HttpServletRequest request) {
        Object id = request.getSession().getAttribute(USER_ID);
        return (Integer) id;
    }

    public static void setAdmin(HttpServletRequest request, boolean admin) {
        request.getSession().setAttribute(ADMIN, admin);
    }

    public static Boolean isAdmin(HttpServletRequest request) {
        Object admin = request.getSession().getAttribute(ADMIN);
        return (Boolean) admin;
    }

    public static void setLocale(HttpServletRequest request, String locale) {
        request.getSession().setAttribute(LOCALE, locale);
    }

    public static String getLocale(HttpServletRequest request) {
        Object locale = request.getSession().getAttribute(LOCALE);
        return (String) locale;
    }

    public static void setPage(HttpServletRequest request, String page) {
        request.getSession().setAttribute(PAGE, page);
    }

    public static String getPage(HttpServletRequest request) {
        Object page = request.getSession().getAttribute(PAGE);
        return (String) page;
    }
}

