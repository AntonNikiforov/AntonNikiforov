package by.training.library.util;

import java.text.SimpleDateFormat;

public class DateHelper {

    public static void main(String[] args) {
        System.out.println(getCurrentDate());
    }

    public static java.sql.Date getCurrentDate() {

        return new java.sql.Date(new java.util.Date().getTime());
    }


}
