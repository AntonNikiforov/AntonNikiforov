package by.training.library.util;

public class DateHelper {

    public static void main(String[] args) {
        String str = null;
        System.out.println(Integer.valueOf(str));
    }

    public static java.sql.Date getCurrentDate() {

        return new java.sql.Date(new java.util.Date().getTime());
    }


}
