package com.ecn.birthday;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

public class Utils {
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) java.util.concurrent.Executors.newFixedThreadPool(2);

    public static final String DATABASE_NAME = "birthday_db";
    public static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DISPLAY_DATE_FORMAT = "dd - MM MMMM - yyyy";
    public static String formatDatabaseDate(Date birthdate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATABASE_DATE_FORMAT);
        return simpleDateFormat.format(birthdate);
    }

    public static String formatDisplayDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }
}
