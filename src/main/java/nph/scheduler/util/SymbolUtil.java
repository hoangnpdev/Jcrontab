package nph.scheduler.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SymbolUtil {

    /**
     *
     * @param sign: [today, yesterday]
     * @return yyyyMMdd
     */
    public static String getSignValue(String sign) {
        switch (sign) {
            case "today":
                return getToday();
            case "yesterday":
                return getYesterday();
            case "month_start":
                return getMonthStart();
            case "month_end":
                return getMonthEnd();
            case "last_month_start":
                return getLastMonthStart();
            case "last_month_end":
                return getLastMonthEnd();
            case "4_day_ago":
                return dayAgo(4);
            default:
                return "";
        }
    }

    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    public static String getYesterday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -1);
        return format.format(calendar.getTime());
    }

    public static String dayAgo(int nDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, -nDay);
        return format.format(calendar.getTime());
    }

    public static String getMonthStart() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DATE, 1);
        return format.format(calendar.getTime());

    }

    public static String getMonthEnd() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return format.format(calendar.getTime());

    }

    public static String getLastMonthStart() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        return format.format(calendar.getTime());
    }

    public static String getLastMonthEnd() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        return format.format(calendar.getTime());
    }


}
