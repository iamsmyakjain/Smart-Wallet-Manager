package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Date parseDate(String dateStr) throws ParseException {
        return dateFormat.parse(dateStr);
    }
    
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
