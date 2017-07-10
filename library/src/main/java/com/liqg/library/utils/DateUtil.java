package com.liqg.library.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liqg on 2015/11/4.
 */
public class DateUtil {

    public final static String DATA_FORMAT_DATE = "yyyy-MM-dd"; //  1990-01-01
    public final static String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATA_FORMAT_DAY = "yyyyMMdd";
    public final static String DATA_FORMAT_MICROSECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static String DATA_FORMAT_CROSECOND = "yyyyMMddHHmmss";

    /**
     * @param format
     * @return
     */
    public static String getCurrentDateTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     *
     * @param timetamp 1358928243000
     * @param format   yyyy-MM-dd HH:mm:ss
     * @return 2012-12-11 15:03:15
     */
    public static String TimeStamp2DateTime(Long timetamp, String format) {
        try {
            String date = new SimpleDateFormat(format)
                    .format(new Date(timetamp));
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return 1 if data1 before data2
     * 2 if data1 after data2
     * 0 if data1 == data2
     * -1 if Exception
     */
    public static int compareDate(String date1, String date2) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            int result = dt1.compareTo(dt2);
            if (result < 0) {
                return 1;
            } else if (result > 0) {
                return 2;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }

    }
}
