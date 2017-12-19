package com.dax.lib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static final String STANDARD_TIME_FULL = "yyyy-MM-dd HH:mm:ss";

    public static long parseStandardTime(String timeDesc) {
        return parseTime(timeDesc, STANDARD_TIME_FULL);
    }

    public static long parseTime(String timeDesc, String pattern) {
        SimpleDateFormat format1 = new SimpleDateFormat(pattern);
        try {
            Date date = format1.parse(timeDesc);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String formatTime(long time, String format) {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(new Date(time));
    }

}
