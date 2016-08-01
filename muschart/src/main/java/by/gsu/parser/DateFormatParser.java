package by.gsu.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateFormatParser {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static Date stringToDate(final String date) throws ParseException {
        return FORMATTER.parse(date);
    }

    public static String dateToString(final Date date) {
        return FORMATTER.format(date);
    }

}
